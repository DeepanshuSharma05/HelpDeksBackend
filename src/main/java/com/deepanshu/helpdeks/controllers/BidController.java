package com.deepanshu.helpdeks.controllers;

import com.deepanshu.helpdeks.dto.BidDto;
import com.deepanshu.helpdeks.entities.Bid;
import com.deepanshu.helpdeks.services.BidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bids") // Standardized base path prefix
public class BidController {

    private final BidService bidService; // Injected service

    @PostMapping("/create/{workPostId}")
    public ResponseEntity<Bid> registerBid(
            Principal principal,
            @PathVariable Long workPostId,
            @Valid @RequestBody BidDto bidDto
    ) {
        String currentUsername = principal.getName();
        Bid createdBid = bidService.createBid(currentUsername, bidDto, workPostId);
        return new ResponseEntity<>(createdBid, HttpStatus.CREATED);
    }

    @GetMapping("/post/{workPostId}")
    public ResponseEntity<List<Bid>> getBidsForPost(
            Principal principal,
            @PathVariable Long workPostId
    ) {
        List<Bid> bids = bidService.getBidsForWorkPost(principal.getName(), workPostId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/my-bids")
    public ResponseEntity<List<Bid>> getMyBids(Principal principal) {
        List<Bid> bids = bidService.getMyBids(principal.getName());
        return ResponseEntity.ok(bids);
    }
}