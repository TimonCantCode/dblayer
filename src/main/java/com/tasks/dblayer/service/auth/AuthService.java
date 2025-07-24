package com.tasks.dblayer.service.auth;

import com.tasks.dblayer.model.UserProfile;
import com.tasks.dblayer.model.dto.LogInRequest;
import com.tasks.dblayer.model.dto.SignUpRequest;
import com.tasks.dblayer.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserProfile register(SignUpRequest dto) {
        if (this.userProfileRepository.findByUserName(dto.userName()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setPassword(passwordEncoder.encode(dto.password()));
        userProfile.setUserName(dto.userName());
        userProfile.setEmail(dto.email());
        userProfile.setBio(dto.bio());
        userProfile.setFullName(dto.fullName());
        userProfile.setLocation(dto.location());
        return this.userProfileRepository.save(userProfile);
    }    
    
    public UserProfile authenticate(String userName, String password) {
        UserProfile userProfile = this.userProfileRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));
        
        if (!this.passwordEncoder.matches(password, userProfile.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        return userProfile;
    }
    
    public UserProfile login(LogInRequest dto, HttpServletRequest request) {
        
        UserProfile user = this.authenticate(dto.userName(), dto.password());
        
        UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authtoken);
        SecurityContextHolder.setContext(context);
        
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );
        
        return user;
    }
}
