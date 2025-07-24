package com.tasks.dblayer.controller.auth;

import com.tasks.dblayer.model.UserProfile;
import com.tasks.dblayer.model.dto.LogInRequest;
import com.tasks.dblayer.model.dto.SignUpRequest;
import com.tasks.dblayer.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String signup(@ModelAttribute SignUpRequest dto) {
        try {
            this.authService.register(dto);
            return "redirect:login";
        } catch (IllegalArgumentException e) {
            return "redirect:/signup_error";
        }
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute LogInRequest dto, HttpServletRequest request) {
        try {
            UserProfile user = this.authService.login(dto, request);
            return "redirect:/show/" + user.getId();
        } catch (IllegalArgumentException ex) {
            return "redirect:/signup_error";
        }
    }
}