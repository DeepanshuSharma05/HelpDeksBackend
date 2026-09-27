package com.deepanshu.helpdeks.controllers;

import com.deepanshu.helpdeks.dto.WorkPostDto;
import com.deepanshu.helpdeks.entities.WorkPost;
import com.deepanshu.helpdeks.services.WorkPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts") // Clean base path
public class WorkPostController {

    private final WorkPostService workPostService;

    @PostMapping("/create")
    public ResponseEntity<WorkPost> createWorkPost(
            Principal principal,
            @Valid @RequestPart("post") WorkPostDto workPostDto, // Receives the JSON data part
            @RequestPart("file") MultipartFile file             // Receives the file part
    ) {
        String currentUsername = principal.getName();

        // Pass everything to the service
        WorkPost createdPost = workPostService.createWorkPost(currentUsername, workPostDto, file);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/my-posts")
    public ResponseEntity<List<WorkPost>> getMyWorkPosts(Principal principal) {
        String currentUsername = principal.getName();
        List<WorkPost> posts = workPostService.getWorkPosts(currentUsername);
        return ResponseEntity.ok(posts);
    }
}