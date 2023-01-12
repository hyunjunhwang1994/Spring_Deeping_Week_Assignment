package com.sparta.spring_deeping_week_assignment.controller;

import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.LikePostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikePostController {

    private final LikePostService likePostService;

    @ApiOperation(
            value = "게시글 좋아요 클릭"
            , notes = "게시글의 ID를 통해 좋아요를 생성합니다.")
    @PostMapping("/like/posts/{posts_id}")
    public LikeResponseDto likeInsertPost(@PathVariable("posts_id") Long postId,
                                          @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likePostService.likeInsertPost(postId, userDetails.getUser());
    }

    @ApiOperation(
            value = "게시글 좋아요 해제"
            , notes = "게시글의 ID를 통해 좋아요를 해제(삭제)합니다.")
    @DeleteMapping("/like/posts/{posts_id}")
    public LikeResponseDto likeDeletePost(@PathVariable("posts_id") Long postId,
                                          @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likePostService.likeDeletePost(postId, userDetails.getUser());
    }
}
