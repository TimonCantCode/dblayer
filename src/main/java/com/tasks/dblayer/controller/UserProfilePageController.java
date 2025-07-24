package com.tasks.dblayer.controller;

import com.tasks.dblayer.model.UserProfile;
import com.tasks.dblayer.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserProfilePageController {
    
    private final UserProfileService userProfileService;
    
    @Autowired
    public UserProfilePageController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
    
    @GetMapping("/show/{id}")
    public String showUserProfilePage(@PathVariable Long id, Model model) {
        Optional<UserProfile> userProfile = this.userProfileService.getUserProfileById(id);
        if (userProfile.isPresent()) {
            model.addAttribute("user", userProfile.get());
            return "profile";
        } else  {
            return "user-not-found";
        }
    }
}
