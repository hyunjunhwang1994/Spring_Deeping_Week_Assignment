package com.sparta.spring_deeping_week_assignment.service;


import com.sparta.spring_deeping_week_assignment.dto.*;
import com.sparta.spring_deeping_week_assignment.entity.*;
import com.sparta.spring_deeping_week_assignment.jwt.JwtUtil;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.CommentRepository;
import com.sparta.spring_deeping_week_assignment.repository.LikeCommentsRepository;
import com.sparta.spring_deeping_week_assignment.repository.PostRepository;
import com.sparta.spring_deeping_week_assignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeCommentsRepository likeCommentsRepository;

    @Transactional
    public CommentsResponseDto createComments(Long postId, CommentsRequestDto requestDto, User user) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (!optionalPost.isPresent()) {
            throw new IllegalArgumentException(ResponseMessage.NOT_FOUND_POST);
        }

        Post post = optionalPost.get();

        Comment comment = new Comment(requestDto, user, post);
        commentRepository.save(comment);

        PostCommentResponseDto postCommentResponseDto = new PostCommentResponseDto(comment, 0);
        return CommentsResponseDto.responseDto(StatusCode.OK
                , ResponseMessage.CREATE_COMMENT_SUCCESS, postCommentResponseDto);
    }

    @Transactional
    public CommentsResponseDto updateComments(Long commentsId, CommentsRequestDto requestDto, User user) {
        // ** ADMIN **
        if (user.getRole() == UserRoleEnum.ADMIN) {
            Optional<Comment> commentOptional = commentRepository.findById(commentsId);
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.updateComment(requestDto);

                // 좋아요 갯수 반환
                List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());
                PostCommentResponseDto postCommentResponseDto =
                        new PostCommentResponseDto(comment, likeCommentsList.size());

                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.UPDATE_COMMENT_SUCCESS, postCommentResponseDto);
            } else {
                throw new IllegalArgumentException(ResponseMessage.NOT_FOUND_POST);
            }
        }

        // ** USER **
        Comment comment = commentRepository.findByIdAndUser_Id(commentsId, user.getId());
        if (comment != null) {
            comment.updateComment(requestDto);

            // 좋아요 갯수 반환
            List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

            PostCommentResponseDto postCommentResponseDto =
                    new PostCommentResponseDto(comment, likeCommentsList.size());

            return CommentsResponseDto.responseDto(StatusCode.OK
                    , ResponseMessage.UPDATE_COMMENT_SUCCESS, postCommentResponseDto);

        } else {
            throw new IllegalArgumentException(ResponseMessage.UPDATE_COMMENT_FAIL);
        }
    }


    public CommentsResponseDto deleteComments(Long commentsId, User user) {
        // ** ADMIN **
        if (user.getRole() == UserRoleEnum.ADMIN) {
            Optional<Comment> commentOptional = commentRepository.findById(commentsId);

            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                commentRepository.deleteById(commentsId);

                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.DELETE_COMMENT_SUCCESS, comment.getContents());
            } else {
                throw new IllegalArgumentException(ResponseMessage.NOT_FOUND_POST);
            }

        }


        // ** USER **
        Comment comment = commentRepository.findByIdAndUser_Id(commentsId, user.getId());
        if (comment != null) {
            commentRepository.deleteById(commentsId);

            return CommentsResponseDto.responseDto(StatusCode.OK
                    , ResponseMessage.DELETE_COMMENT_SUCCESS, comment.getContents());

        } else {
            throw new IllegalArgumentException(ResponseMessage.DELETE_COMMENT_FAIL);
        }


    }

}

