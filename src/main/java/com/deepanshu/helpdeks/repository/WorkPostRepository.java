package com.deepanshu.helpdeks.repository;

import com.deepanshu.helpdeks.entities.User;
import com.deepanshu.helpdeks.entities.WorkPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkPostRepository extends JpaRepository<WorkPost,Long> {

    List<WorkPost> findByUser(User user);
}
