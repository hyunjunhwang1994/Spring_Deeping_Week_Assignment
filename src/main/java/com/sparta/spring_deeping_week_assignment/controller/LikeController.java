package com.sparta.spring_deeping_week_assignment.controller;


import com.sparta.spring_deeping_week_assignment.dto.LikeResponseDto;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;


    @GetMapping("/likepost/{postId}")
    public LikeResponseDto likePost(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeService.likePostButton(postId, userDetails.getUser());
    }

    @GetMapping("/likecomments/{commentId}")
    public LikeResponseDto likeComments(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return likeService.likeCommentsButton(commentId, userDetails.getUser());
    }

}
