package com.sparta.spring_deeping_week_assignment.repository;

import com.sparta.spring_deeping_week_assignment.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    int deleteByIdAndUser_Id(Long id, Long userId);



    Post findByIdAndUser_Id(Long id, Long userId);



}
