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
}
