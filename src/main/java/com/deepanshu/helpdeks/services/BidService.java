package com.deepanshu.helpdeks.services;


import com.deepanshu.helpdeks.dto.BidDto;
import com.deepanshu.helpdeks.entities.Bid;
import com.deepanshu.helpdeks.entities.User;
import com.deepanshu.helpdeks.entities.WorkPost;
import com.deepanshu.helpdeks.repository.BidRepository;
import com.deepanshu.helpdeks.repository.UserRepository;
import com.deepanshu.helpdeks.repository.WorkPostRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BidService {

    private final UserRepository userRepository;
    private final WorkPostRepository workPostRepository;
    private final BidRepository bidRepository;

    public Bid createBid(String currentUser, BidDto bidDto , Long workPostId){


        //finding if user exists or not
        User user = userRepository.findByUserName(currentUser)
                .orElseThrow(()-> new RuntimeException("user not found"));

        //checking is the user is SP or not
        if(!Objects.equals(user.getRole(), "SERVICE_PROVIDER")){
            throw new RuntimeException("Only service provider can make a bid");
        }

        WorkPost workPost = workPostRepository.findById(workPostId)
                .orElseThrow(()->new RuntimeException("Post doesnt exist"));


        Bid bid = new Bid();
        bid.setAmount(bidDto.getAmount());
        bid.setWorkPost(workPost);
        bid.setServiceProvider(user);
        bid.setStatus("PENDING"); // Set default status

        return bidRepository.save(bid);

    }

    public List<Bid> getBidsForWorkPost(String currentUsername ,Long workPostId){
            User user = userRepository.findByUserName(currentUsername)
                    .orElseThrow(()->new RuntimeException("user not found"));

            WorkPost workPost = workPostRepository.findById(workPostId)
                    .orElseThrow(()-> new RuntimeException("Work POST NOT FOUND"));

            if(!Objects.equals(workPost.getUser().getId() , user.getId())){
                throw new RuntimeException("You can only view bids for your own posts");
            }

        return bidRepository.findByWorkPost(workPost);
    }

    // Get all bids made by the logged-in Service Provider
    public List<Bid> getMyBids(String currentUsername) {
        User user = userRepository.findByUserName(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!Objects.equals(user.getRole(), "SERVICE_PROVIDER")) {
            throw new RuntimeException("Only service providers can view their bids this way");
        }

        return bidRepository.findByServiceProvider(user);
    } // done for the day

    public Bid updateBidStatus(String currentUsername, Long bidId, String newStatus) {
        // 1. Find the logged-in user
        User user = userRepository.findByUserName(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Ensure the user is a CONSUMER
        if (!Objects.equals(user.getRole(), "CONSUMER")) {
            throw new RuntimeException("Only consumers can accept or reject bids");
        }

        // 3. Find the bid
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found"));

        // 4. Verify that the consumer owns the work post this bid belongs to
        WorkPost workPost = bid.getWorkPost();
        if (!Objects.equals(workPost.getUser().getId(), user.getId())) {
            throw new RuntimeException("You can only manage bids for your own work posts");
        }

        // 5. Validate and update status (ACCEPTED or REJECTED)
        String upperStatus = newStatus.toUpperCase();
        if (!upperStatus.equals("ACCEPTED") && !upperStatus.equals("REJECTED")) {
            throw new RuntimeException("Invalid status. Use ACCEPTED or REJECTED");
        }

        bid.setStatus(upperStatus);

        // Optional: If this bid is accepted, you could loop through other bids
        // for this workPost and set them to REJECTED automatically.

        return bidRepository.save(bid);
    }


}
