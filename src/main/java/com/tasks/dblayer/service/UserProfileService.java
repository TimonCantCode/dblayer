package com.tasks.dblayer.service;

import com.tasks.dblayer.model.UserProfile;
import com.tasks.dblayer.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {
    
    private final UserProfileRepository userProfileRepository;
    
    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
    
    public UserProfile createUser(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }
    
    public void deleteUser(UserProfile userProfile) {
        this.userProfileRepository.delete(userProfile);
    }
    
    public List<UserProfile> getAllUser() {
        return userProfileRepository.findAll();
    }
    
    public Optional<UserProfile> getUserProfileById(Long id) {
        return userProfileRepository.findById(id);
    }
}
