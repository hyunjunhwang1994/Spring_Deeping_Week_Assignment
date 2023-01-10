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
    public CommentsResponseDto createComments(Long id, CommentsRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {

                claims = jwtUtil.getUserInfoFromToken(token);


            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Optional<User> optionalUser = userRepository.findByUsername(claims.getSubject());

            if (!optionalUser.isPresent()) {
                return CommentsResponseDto.responseDto(StatusCode.BAD_REQUEST
                        , ResponseMessage.USER_NOT_FOUND, null);
            }

            User user = optionalUser.get();


            Optional<Post> optionalPost = postRepository.findById(id);

            if (!optionalPost.isPresent()) {
                return CommentsResponseDto.responseDto(StatusCode.BAD_REQUEST
                        , ResponseMessage.NOT_FOUND_POST, null);
            }

            Post post = optionalPost.get();


            Comment comment = new Comment(requestDto,user,post);

            commentRepository.save(comment);

            PostCommentResponseDto postCommentResponseDto = new PostCommentResponseDto(comment, 0);

            return CommentsResponseDto.responseDto(StatusCode.OK
                    , ResponseMessage.CREATE_COMMENT_SUCCESS, postCommentResponseDto);



        } else {
            return CommentsResponseDto.responseDto(StatusCode.BAD_REQUEST
                    , ResponseMessage.TOKEN_AUTH_ERROR, null);
        }




    }

    @Transactional
    public CommentsResponseDto updateComments(Long id, CommentsRequestDto requestDto, HttpServletRequest request) {

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


            Comment comment = commentRepository.findByIdAndUser_Id(id, user.getId());




            if (user.getRole() == UserRoleEnum.ADMIN) {
                Optional<Comment> commentAdmin = commentRepository.findById(id);

                if (commentAdmin.isPresent()) {
                    Comment temp = commentAdmin.get();
                    temp.updateComment(requestDto);

                    List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

                    PostCommentResponseDto postCommentResponseDto = new PostCommentResponseDto(temp, likeCommentsList.size());

                    return CommentsResponseDto.responseDto(StatusCode.OK
                            , ResponseMessage.UPDATE_COMMENT_SUCCESS, postCommentResponseDto);

                }

            }











            if(comment != null){

                comment.updateComment(requestDto);
                List<LikeComments> likeCommentsList = likeCommentsRepository.findByCommentId(comment.getId());

                PostCommentResponseDto postCommentResponseDto = new PostCommentResponseDto(comment, likeCommentsList.size());

                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.UPDATE_COMMENT_SUCCESS, postCommentResponseDto);

            }else{
                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.UPDATE_COMMENT_FAIL, null);
            }







        } else {
            return CommentsResponseDto.responseDto(StatusCode.OK
                    , ResponseMessage.UPDATE_COMMENT_FAIL, null);
        }



    }


    public CommentsResponseDto deleteComments(Long id, HttpServletRequest request) {

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

            Comment comment = commentRepository.findByIdAndUser_Id(id, user.getId());





            if (user.getRole() == UserRoleEnum.ADMIN) {
                Optional<Comment> commentAdmin = commentRepository.findById(id);

                if (commentAdmin.isPresent()) {
                    Comment temp = commentAdmin.get();

                    commentRepository.deleteById(id);

                    return CommentsResponseDto.responseDto(StatusCode.OK
                            , ResponseMessage.DELETE_COMMENT_SUCCESS, temp.getContents());


                }

            }











            if(comment != null){
                commentRepository.deleteById(id);

                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.DELETE_COMMENT_SUCCESS, comment.getContents());

            }else{
                return CommentsResponseDto.responseDto(StatusCode.OK
                        , ResponseMessage.DELETE_COMMENT_FAIL, null);
            }


        } else {
            return CommentsResponseDto.responseDto(StatusCode.OK
                    , ResponseMessage.DELETE_COMMENT_FAIL, null);
        }

    }
}
