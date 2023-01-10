package com.sparta.spring_deeping_week_assignment.service;


import com.sparta.spring_deeping_week_assignment.dto.*;
import com.sparta.spring_deeping_week_assignment.entity.*;
import com.sparta.spring_deeping_week_assignment.jwt.JwtUtil;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.*;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final LikePostRepository likePostRepository;
    private final LikeCommentsRepository likeCommentsRepository;
    private final JwtUtil jwtUtil;


    public PostResponseDto createPost(PostRequestDto requestDto, User user) {



            Post post = new Post(requestDto, user);
            postRepository.save(post);


            List<Comment> commentList = commentRepository.findAllByPost_IdOrderByCreatedAtDesc(post.getId());
            List<PostCommentResponseDto> postCommentResponseDto = new ArrayList<>();

            for (Comment comment : commentList) {               // 포스트를 만들 때 응답이므로 좋아요 수 0
                postCommentResponseDto.add(new PostCommentResponseDto(comment, 0));
            }



            PostResponseDto postResponseDto = new PostResponseDto(post, postCommentResponseDto, 0);

            return postResponseDto;

        }




    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {


        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();






        List<Comment> commentList;
        List<PostCommentResponseDto> postCommentResponseDto = new ArrayList<>();

        List<LikePost> likePostsList;
        List<LikeComments> likeCommentsList;

        for (Post post : postList) {

            commentList = commentRepository.findAllByPost_IdOrderByCreatedAtDesc(post.getId());
            likePostsList = likePostRepository.findByPostId(post.getId());




            for (Comment comment : commentList) {
                likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

                postCommentResponseDto.add(new PostCommentResponseDto(comment, likeCommentsList.size()));

            }



            PostResponseDto postResponseDto = new PostResponseDto(post, postCommentResponseDto,likePostsList.size());
            postResponseDtoList.add(postResponseDto);

        }

        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto readPost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );



        List<Comment> commentList = commentRepository.findAllByPost_IdOrderByCreatedAtDesc(post.getId());
        List<PostCommentResponseDto> postCommentResponseDto = new ArrayList<>();

        List<LikePost> likePostsList;
        likePostsList = likePostRepository.findByPostId(post.getId());

        List<LikeComments> likeCommentsList;

        for (Comment comment : commentList) {
            likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

            postCommentResponseDto.add(new PostCommentResponseDto(comment, likeCommentsList.size()));

        }

        PostResponseDto postResponseDto = new PostResponseDto(post, postCommentResponseDto, likePostsList.size());



        return postResponseDto;
    }



    @Transactional
    public PostDeleteResponseDto deletePost(Long id, User user) {


            if (user.getRole() == UserRoleEnum.ADMIN) {
                Optional<Post> post = postRepository.findById(id);
                if (post.isPresent()) {

                    commentRepository.deleteByPost_Id(id);
                    postRepository.deleteById(id);

                    PostDeleteResponseDto postDeleteResponseDto = new PostDeleteResponseDto(
                            StatusCode.OK, ResponseMessage.POST_DELETE_SUCCESS,1);
                    return postDeleteResponseDto;

                }else{

                    PostDeleteResponseDto postDeleteResponseDto = new PostDeleteResponseDto(
                            StatusCode.OK, ResponseMessage.POST_DELETE_FAIL,0);
                    return postDeleteResponseDto;

                }
            }



            Post post = postRepository.findByIdAndUser_Id(id, user.getId());

            if(post != null){

                if(post.getUser().getId().equals(user.getId())){
                    commentRepository.deleteByPost_Id(id);
                    postRepository.deleteById(id);
                    PostDeleteResponseDto postDeleteResponseDto = new PostDeleteResponseDto(
                            StatusCode.OK, ResponseMessage.POST_DELETE_SUCCESS,1);
                    return postDeleteResponseDto;
                }else{

                    throw new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND);
                }

            } else {
                PostDeleteResponseDto postDeleteResponseDto = new PostDeleteResponseDto(
                        StatusCode.OK, ResponseMessage.POST_DELETE_FAIL,0);
                return postDeleteResponseDto;
            }



    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {



        String token = jwtUtil.resolveToken(request);
        Claims claims;


        if (token != null) {

            if (jwtUtil.validateToken(token)) {

                claims = jwtUtil.getUserInfoFromToken(token);

            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );



            Post post = postRepository.findByIdAndUser_Id(id, user.getId());


            if (user.getRole() == UserRoleEnum.ADMIN) {
                Optional<Post> postAdmin = postRepository.findById(id);

                if (postAdmin.isPresent()) {
                    Post temp = postAdmin.get();

                    temp.updatePost(requestDto);
                    List<Comment> commentList = commentRepository.findAllByPost_IdOrderByCreatedAtDesc(temp.getId());
                    List<PostCommentResponseDto> postCommentResponseDto = new ArrayList<>();

                    List<LikePost> likePostsList;
                    likePostsList = likePostRepository.findByPostId(post.getId());

                    List<LikeComments> likeCommentsList;
                    for (Comment comment : commentList) {
                        likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());
                        postCommentResponseDto.add(new PostCommentResponseDto(comment, likeCommentsList.size()));

                    }

                    PostResponseDto postResponseDto = new PostResponseDto(temp, postCommentResponseDto, likePostsList.size());

                    return postResponseDto;

                }

            }


            if(post != null){

                post.updatePost(requestDto);
                List<Comment> commentList = commentRepository.findAllByPost_IdOrderByCreatedAtDesc(post.getId());
                List<PostCommentResponseDto> postCommentResponseDto = new ArrayList<>();

                List<LikePost> likePostsList;
                likePostsList = likePostRepository.findByPostId(post.getId());

                List<LikeComments> likeCommentsList;
                for (Comment comment : commentList) {
                    likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());
                    postCommentResponseDto.add(new PostCommentResponseDto(comment, likeCommentsList.size()));

                }

                PostResponseDto postResponseDto = new PostResponseDto(post, postCommentResponseDto, likePostsList.size());



                return postResponseDto;
            }else{
                return null;
            }

        } else {
            return null;
        }




















    }





}
