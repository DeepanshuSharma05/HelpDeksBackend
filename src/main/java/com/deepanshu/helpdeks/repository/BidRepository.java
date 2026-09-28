package com.deepanshu.helpdeks.repository;


import com.deepanshu.helpdeks.entities.Bid;
import com.deepanshu.helpdeks.entities.User;
import com.deepanshu.helpdeks.entities.WorkPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findByWorkPost(WorkPost workPost);
    List<Bid> findByServiceProvider(User serviceProvider);
}
