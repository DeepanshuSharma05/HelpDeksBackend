package com.deepanshu.helpdeks.services;

import com.deepanshu.helpdeks.dto.ProfileDto;
import com.deepanshu.helpdeks.entities.Profile;
import com.deepanshu.helpdeks.entities.User;
import com.deepanshu.helpdeks.repository.ProfileRepository;
import com.deepanshu.helpdeks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor



public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

        public Profile createProfile(String currentUser ,ProfileDto profileDto){

            //finding the current user by user repository

           User user = userRepository.findByUserName(currentUser)
                   .orElseThrow(()-> new RuntimeException("User not found"));

           //now checking if the current user has a profile or not

           if(profileRepository.existsByUser(user)){
               throw new RuntimeException("Profile already exists");
           }

           // saving the new profile


            Profile profile = new Profile();

           profile.setBio(profileDto.getBio());
           profile.setExperience(profileDto.getExperience());
           profile.setFullName(profileDto.getFullName());
           profile.setLinkedinProfile(profileDto.getLinkedinProfile());
           profile.setLocation(profileDto.getLocation());
           profile.setSkills(profileDto.getSkills());

           profile.setUser(user);

           return profileRepository.save(profile);


    }

    public Profile getProfileByUsername(String username){
            User user = userRepository.findByUserName(username)
                    .orElseThrow(()-> new RuntimeException("User not found"));

            return profileRepository.findByUser(user)
                    .orElseThrow(()->new RuntimeException("Create profile First"));
    }


}
