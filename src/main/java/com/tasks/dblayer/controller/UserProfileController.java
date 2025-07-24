package com.tasks.dblayer.controller;

import com.tasks.dblayer.model.UserProfile;
import com.tasks.dblayer.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    
    private final UserProfileService userProfileService;
    
    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
    
    @PostMapping("/create")
    public UserProfile createUser(@RequestBody UserProfile userProfile) {
        return this.userProfileService.createUser(userProfile);
    }
    
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserProfile userProfile) {
        this.userProfileService.deleteUser(userProfile);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        Optional<UserProfile> userProfile = this.userProfileService.getUserProfileById(id);
        userProfile.ifPresent(this.userProfileService::deleteUser);
    }
    
    @GetMapping("/all")
    public List<UserProfile> getAllUsers() {
        return this.userProfileService.getAllUser();
    }
    
    @GetMapping("/{id}")
    public Optional<UserProfile> getUserProfileById(@PathVariable Long id) {
        return this.userProfileService.getUserProfileById(id);
    }
}
