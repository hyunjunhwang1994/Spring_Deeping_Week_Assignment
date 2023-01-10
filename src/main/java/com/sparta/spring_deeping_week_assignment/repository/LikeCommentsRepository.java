package com.sparta.spring_deeping_week_assignment.repository;

import com.sparta.spring_deeping_week_assignment.entity.LikeComments;
import com.sparta.spring_deeping_week_assignment.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCommentsRepository extends JpaRepository<LikeComments, Long> {

    Optional<LikeComments> findByCommentIdAndUserId(Long commentsId, Long userId);

    void deleteByCommentIdAndUserId(Long commentsId, Long userId);


    List<LikeComments> findByCommentId(Long commentsId);
}