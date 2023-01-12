package com.sparta.spring_deeping_week_assignment.controller;


import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.LikeCommentService;
import com.sparta.spring_deeping_week_assignment.service.LikePostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeCommentController {

    private final LikeCommentService likeCommentService;



    @ApiOperation(
            value = "댓글 좋아요 클릭"
            , notes = "댓글의 ID를 통해 좋아요를 생성합니다.")
    @PostMapping("/like/comments/{comments_id}")
    public LikeResponseDto likeInsertComment(@PathVariable("comments_id") Long commentId,
                                             @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeCommentService.likeInsertComment(commentId, userDetails.getUser());
    }

    @ApiOperation(
            value = "댓글 좋아요 해제"
            , notes = "댓글의 ID를 통해 좋아요를 해제(삭제)합니다.")
    @DeleteMapping("/like/comments/{comments_id}")
    public LikeResponseDto likeDeleteComment(@PathVariable("comments_id") Long commentId,
                                             @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeCommentService.likeDeleteComment(commentId, userDetails.getUser());
    }


}
