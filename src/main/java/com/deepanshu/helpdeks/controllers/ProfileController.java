package com.deepanshu.helpdeks.controllers;


import com.deepanshu.helpdeks.dto.ProfileDto;
import com.deepanshu.helpdeks.entities.Profile;
import com.deepanshu.helpdeks.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")

public class ProfileController {

    private final ProfileService profileService;

    // create profile endpoint
    @PostMapping("/create")
    public ResponseEntity<Profile> createProfile(Principal principal , @Valid @RequestBody ProfileDto profileDto){

        String userName = principal.getName();

        Profile createdProfile = profileService.createProfile(userName , profileDto);

        return ResponseEntity.ok(createdProfile);

    }

    @GetMapping("/getprofiles")
    public ResponseEntity<Profile> getProfile(Principal principal){
        String userName = principal.getName();

        Profile profile = profileService.getProfileByUsername(userName);

        return ResponseEntity.ok(profile);
    }

}
