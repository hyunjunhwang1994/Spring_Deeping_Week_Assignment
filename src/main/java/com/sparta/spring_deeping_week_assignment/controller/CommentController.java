package com.sparta.spring_deeping_week_assignment.controller;


import com.sparta.spring_deeping_week_assignment.dto.CommentsRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.CommentsResponseDto;
import com.sparta.spring_deeping_week_assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{id}")
    public CommentsResponseDto createComments(@PathVariable Long id, @RequestBody CommentsRequestDto requestDto, HttpServletRequest request) {

        return commentService.createComments(id, requestDto, request);
    }

    @PutMapping("/comments/{id}")
    public CommentsResponseDto updateComments(@PathVariable Long id, @RequestBody CommentsRequestDto requestDto, HttpServletRequest request) {
        return commentService.updateComments(id, requestDto, request);
    }

    @DeleteMapping("/comments/{id}")
    public CommentsResponseDto deleteComments(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComments(id, request);
    }
}

