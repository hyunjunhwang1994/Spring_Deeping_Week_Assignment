package com.sparta.spring_deeping_week_assignment.service;

import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.PostResponseDto;
import com.sparta.spring_deeping_week_assignment.entity.*;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.LikePostRepository;
import com.sparta.spring_deeping_week_assignment.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikePostService {
    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;

    @Transactional  // 글 좋아요 생성
    public LikeResponseDto likeInsertPost(Long postId, User user) {

        Optional<LikePost> likePostOptional = likePostRepository.findByPostIdAndUserId(postId, user.getId());
        Optional<Post> postOptional = postRepository.findById(postId);

        // 해당 게시글 자체가 없다면 튕구기
        if (postOptional.isEmpty()) throw new IllegalArgumentException(ResponseMessage.NOT_FOUND_POST);

        // 해당 게시글에 해당 유저의 좋아요가 없을 때 만 진행
        if (likePostOptional.isEmpty()) {
            Post post = postOptional.get();

            // 해당 게시글, 해당 유저의 좋아요 클릭(생성)
            LikePost likePost = new LikePost(post, user);
            likePostRepository.save(likePost);

            // 현재 게시글 좋아요 총 갯수 구하기.
            List<LikePost> likePostsList = likePostRepository.findByPostId(post.getId());


            // 성공 코드, 성공 메시지, 해당 게시글 좋아요 총 갯수 리턴
            return new LikeResponseDto(StatusCode.OK, ResponseMessage.CREATE_LIKE_SUCCESS, likePostsList.size());
        }

        // 해당 게시글에 이미 해당 유저의 좋아요가 있다면
        return new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.CREATE_LIKE_FAIL, null);

    }

    @Transactional  // 글 좋아요 삭제(해제)
    public LikeResponseDto likeDeletePost(Long postId, User user) {

        Optional<LikePost> likePostOptional = likePostRepository.findByPostIdAndUserId(postId, user.getId());
        Optional<Post> postOptional = postRepository.findById(postId);

        // 해당 게시글에 해당 유저의 좋아요가 있을 때 만 진행
        if (likePostOptional.isPresent()) {
            Post post = postOptional.get();

            // 해당 게시글, 해당 유저의 좋아요 해제(삭제)
            likePostRepository.deleteByPostIdAndUserId(post.getId(), user.getId());

            // 현재 게시글 좋아요 총 갯수 구하기.
            List<LikePost> likePostsList = likePostRepository.findByPostId(post.getId());

            // 성공 코드, 성공 메시지, 해당 게시글 좋아요 총 갯수 리턴
            return new LikeResponseDto(StatusCode.OK, ResponseMessage.DELETE_LIKE_SUCCESS, likePostsList.size());
        }

        // 해당 게시글에 해당 유저의 좋아요가 없다면
        return new LikeResponseDto(StatusCode.BAD_REQUEST, ResponseMessage.DELETE_LIKE_FAIL, null);

    }


}

