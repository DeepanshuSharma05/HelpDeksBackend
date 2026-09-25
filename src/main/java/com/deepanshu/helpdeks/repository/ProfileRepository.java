package com.deepanshu.helpdeks.repository;

import com.deepanshu.helpdeks.entities.Profile;
import com.deepanshu.helpdeks.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository  extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);

    boolean existsByUser(User user);
}
