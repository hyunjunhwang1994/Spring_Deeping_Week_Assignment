package com.sparta.spring_deeping_week_assignment.repository;

import com.sparta.spring_deeping_week_assignment.entity.LikePost;
import com.sparta.spring_deeping_week_assignment.entity.Post;
import com.sparta.spring_deeping_week_assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {


    Optional<LikePost> findByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);

    List<LikePost>  findByPostId(Long postId);


}
