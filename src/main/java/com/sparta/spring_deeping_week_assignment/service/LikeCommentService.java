package com.sparta.spring_deeping_week_assignment.service;


import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.entity.*;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.CommentRepository;
import com.sparta.spring_deeping_week_assignment.repository.LikeCommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeCommentService {


    private final LikeCommentsRepository likeCommentsRepository;
    private final CommentRepository commentRepository;


    @Transactional  // 댓글 좋아요 생성
    public LikeResponseDto likeInsertComment(Long commentsId, User user) {

        Optional<LikeComments> likeCommentsOptional = likeCommentsRepository.findByCommentIdAndUserId(commentsId, user.getId());
        Optional<Comment> commentOptional = commentRepository.findById(commentsId);

        // 해당 댓글 자체가 없다면 튕구기
        if (commentOptional.isEmpty()) throw new IllegalArgumentException(ResponseMessage.NOT_FOUND_POST);

        // 해당 댓글에 해당 유저의 좋아요가 없을 때 만 진행
        if (likeCommentsOptional.isEmpty()) {
            Comment comment = commentOptional.get();

            // 해당 댓글, 해당 유저의 좋아요 클릭(생성)
            LikeComments likeComments = new LikeComments(comment, user);
            likeCommentsRepository.save(likeComments);

            // 현재 댓글 좋아요 총 갯수 구하기.
            List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

            // 성공 코드, 성공 메시지, 해당 댓글 좋아요 총 갯수 리턴
            return new LikeResponseDto(StatusCode.OK, ResponseMessage.CREATE_LIKE_SUCCESS, likeCommentsList.size());

        }

        // 해당 댓글에 이미 해당 유저의 좋아요가 있다면
        return new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.CREATE_LIKE_FAIL, null);
    }


    @Transactional  // 댓글 좋아요 삭제(해제)
    public LikeResponseDto likeDeleteComment(Long commentsId, User user) {

        Optional<LikeComments> likeCommentsOptional = likeCommentsRepository.findByCommentIdAndUserId(commentsId, user.getId());
        Optional<Comment> commentOptional = commentRepository.findById(commentsId);

        // 해당 댓글에 해당 유저의 좋아요가 있을 때 만 진행
        if (likeCommentsOptional.isPresent()) {
            Comment comment = commentOptional.get();

            // 해당 댓글, 해당 유저의 좋아요 해제(삭제)
            likeCommentsRepository.deleteByCommentIdAndUserId(comment.getId(), user.getId());

            // 현재 댓글 좋아요 총 갯수 구하기.
            List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

            // 성공 코드, 성공 메시지, 해당 댓글 좋아요 총 갯수 리턴
            return new LikeResponseDto(StatusCode.OK, ResponseMessage.DELETE_LIKE_SUCCESS, likeCommentsList.size());

        }

        // 해당 댓글에 이미 해당 유저의 좋아요가 없다면
        return new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.DELETE_LIKE_FAIL, null);
    }

}
