package com.sparta.spring_deeping_week_assignment.controller;

import com.sparta.spring_deeping_week_assignment.Exception.TestException;
import com.sparta.spring_deeping_week_assignment.dto.PostDeleteResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.PostRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.PostResponseDto;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {



        if(requestDto.getContents().equals("욕")){

            throw new IllegalArgumentException("글 못올린다!");
        }
        return postService.createPost(requestDto, userDetails.getUser());
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {


        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto readPost(@PathVariable Long id) {

        PostResponseDto postResponseDto = postService.readPost(id);


        return postResponseDto;
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<PostDeleteResponseDto> deletePost(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {


        PostDeleteResponseDto postDeleteResponseDto = postService.deletePost(id, userDetails.getUser());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json",
                Charset.forName("UTF-8")));

        return new ResponseEntity<>(postDeleteResponseDto, headers, StatusCode.OK);

    }


    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                                      HttpServletRequest request) {

        PostResponseDto postResponseDto = postService.updatePost(id, requestDto, request);


        return postResponseDto;
    }


}
