package com.sparta.spring_deeping_week_assignment.service;


import com.sparta.spring_deeping_week_assignment.dto.CommentsResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.PostResponseDto;
import com.sparta.spring_deeping_week_assignment.entity.*;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.CommentRepository;
import com.sparta.spring_deeping_week_assignment.repository.LikeCommentsRepository;
import com.sparta.spring_deeping_week_assignment.repository.LikePostRepository;
import com.sparta.spring_deeping_week_assignment.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikePostRepository likePostRepository;
    private final LikeCommentsRepository likeCommentsRepository;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;



    @Transactional
    public LikeResponseDto likePostButton(Long postId, User user) {

        Optional<LikePost> result = likePostRepository.findByPostIdAndUserId(postId, user.getId());
        Optional<Post> post = postRepository.findById(postId);

        if (result.isPresent()) {
            // 해당 글에 좋아요 존재
            likePostRepository.deleteByPostIdAndUserId(postId, user.getId());

            List<LikePost> likePostsList;
            likePostsList = likePostRepository.findByPostId(post.get().getId());

            if(post.isPresent()) {

                // Response Body Data의 Data부분에 간단하게 좋아요 타겟 게시글의 정보를 포함하기 위해 작성 (댓글은 null처리)
                PostResponseDto postResponseDto = new PostResponseDto(post.get(), null, likePostsList.size());

                return new LikeResponseDto(StatusCode.OK, ResponseMessage.DELETE_LIKE_SUCCESS, postResponseDto);
            }


        }else{
            // 해당 글에 좋아요 없음
            // 포스트 존재
            if (post.isPresent()) {
                Post inputPost = post.get();
                LikePost likePost = new LikePost(inputPost, user);
                likePostRepository.save(likePost);

                List<LikePost> likePostsList;
                likePostsList = likePostRepository.findByPostId(post.get().getId());

                PostResponseDto postResponseDto = new PostResponseDto(post.get(), null, likePostsList.size());

                return new LikeResponseDto(StatusCode.OK, ResponseMessage.CREATE_LIKE_SUCCESS, postResponseDto);


            }
        }


        LikeResponseDto likePostResponseDto
                = new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_POST, null);

        return likePostResponseDto;


    }

    @Transactional
    public LikeResponseDto likeCommentsButton(Long commentsId, User user) {

        Optional<LikeComments> result = likeCommentsRepository.findByCommentIdAndUserId(commentsId, user.getId());
        Optional<Comment> comment = commentRepository.findById(commentsId);

        if (result.isPresent()) {

            likeCommentsRepository.deleteByCommentIdAndUserId(commentsId, user.getId());

            if(comment.isPresent()) {
                String comments = comment.get().getContents();

                return new LikeResponseDto(StatusCode.OK, ResponseMessage.DELETE_LIKE_SUCCESS, comments);
            }


        }else{
            // 해당 댓글에 좋아요 없음
            // 댓글 존재
            if (comment.isPresent()) {
                LikeComments likeComments = new LikeComments(comment.get(), user);

                likeCommentsRepository.save(likeComments);

                String comments = comment.get().getContents();

                return new LikeResponseDto(StatusCode.OK, ResponseMessage.CREATE_LIKE_SUCCESS, comments);


            }
        }


        LikeResponseDto likeCommentsResponseDto
                = new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_POST, null);

        return likeCommentsResponseDto;



    }


}
