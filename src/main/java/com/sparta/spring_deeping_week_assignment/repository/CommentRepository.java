package com.sparta.spring_deeping_week_assignment.repository;

import com.sparta.spring_deeping_week_assignment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndUser_Id(Long id, Long userId);

    void deleteByPost_Id(Long postId);


    List<Comment> findAllByPost_IdOrderByCreatedAtDesc(Long postId);

}

