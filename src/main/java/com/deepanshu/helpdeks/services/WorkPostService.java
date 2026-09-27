package com.deepanshu.helpdeks.services;

import com.deepanshu.helpdeks.dto.WorkPostDto;
import com.deepanshu.helpdeks.entities.User;
import com.deepanshu.helpdeks.entities.WorkPost;
import com.deepanshu.helpdeks.repository.UserRepository;
import com.deepanshu.helpdeks.repository.WorkPostRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkPostService {

   private final UserRepository userRepository;
   private final WorkPostRepository workPostRepository;

    public WorkPost createWorkPost(String currentUsername, WorkPostDto workPostDto, MultipartFile file){

        User user = userRepository.findByUserName(currentUsername)
                .orElseThrow(()-> new RuntimeException("user not found"));

        if(!"CONSUMER".equals(user.getRole())){
            throw new RuntimeException("Only consumers are allower to post");
        }

        WorkPost workPost= new WorkPost();

        workPost.setTitle(workPostDto.getTitle());
        workPost.setDescription(workPostDto.getDescription());
        workPost.setMaximumPay(workPostDto.getMaximumPay());
        workPost.setMinimumPay(workPostDto.getMinimumPay());
        workPost.setSkillsRequired(workPostDto.getSkillsRequired());
        workPost.setFilepath(workPostDto.getFilepath());


        //if file is provided

        if (file != null && !file.isEmpty()) {
            try {
                // Define folder where files will be saved locally
                String uploadDir = "uploads/work_docs/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create directory if it doesn't exist
                }

                // Generate a unique filename to prevent overwriting files with the same name
                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

                Path filePath = Paths.get(uploadDir + uniqueFilename);

                // Copy file to target location
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Save the file path string into your entity
                workPost.setFilepath(filePath.toString());

            } catch (IOException e) {
                throw new RuntimeException("Failed to store file: " + e.getMessage());
            }
        }

        workPost.setUser(user);
        return workPostRepository.save(workPost);


    }

    public List<WorkPost> getWorkPosts(String currentUsername) {
        User user = userRepository.findByUserName(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return workPostRepository.findByUser(user);
    }
}
