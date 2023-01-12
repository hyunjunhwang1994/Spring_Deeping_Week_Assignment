package com.sparta.spring_deeping_week_assignment.controller;


import com.sparta.spring_deeping_week_assignment.dto.CommentsRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.CommentsResponseDto;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;


    @ApiOperation(
            value = "댓글 작성"
            , notes = "게시글의 ID를 통해 댓글을 작성합니다.")
    @PostMapping("/comments/{posts_id}")
    public CommentsResponseDto createComments(@PathVariable("posts_id") Long postId,
                                              @RequestBody CommentsRequestDto requestDto,
                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComments(postId, requestDto, userDetails.getUser());
    }


    @ApiOperation(
            value = "댓글 수정"
            , notes = "댓글의 ID를 통해 댓글을 수정합니다.")
    @PutMapping("/comments/{comments_id}")
    public CommentsResponseDto updateComments(@PathVariable("comments_id") Long commentId,
                                              @RequestBody CommentsRequestDto requestDto,
                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComments(commentId, requestDto, userDetails.getUser());
    }

    @ApiOperation(
            value = "댓글 삭제"
            , notes = "댓글의 ID를 통해 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{comments_id}")
    public CommentsResponseDto deleteComments(@PathVariable("comments_id") Long commentId,
                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComments(commentId, userDetails.getUser());
    }
}

