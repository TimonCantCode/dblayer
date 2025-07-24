package com.tasks.dblayer.repository;

import com.tasks.dblayer.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findByUserName(String userName);
}
