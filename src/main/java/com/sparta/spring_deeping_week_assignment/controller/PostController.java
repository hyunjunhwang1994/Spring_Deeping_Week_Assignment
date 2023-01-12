package com.sparta.spring_deeping_week_assignment.controller;

import com.sparta.spring_deeping_week_assignment.dto.PostDeleteResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.PostRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.PostResponseDto;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import com.sparta.spring_deeping_week_assignment.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;


    @ApiOperation(
            value = "게시글 작성"
            , notes = "JSON 형식의 게시글 데이터를 받아 게시글을 작성합니다.")
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.createPost(requestDto, userDetails.getUser());
    }


    @ApiOperation(
            value = "전체 게시글 보기"
            , notes = "전체 게시글을 JSON 형식으로 응답 받습니다.")
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @ApiOperation(
            value = "선택 게시글 보기"
            , notes = "게시글 번호를 선택해, 선택 게시글을 JSON 형식으로 응답 받습니다.")
    @GetMapping("/posts/{id}")
    public PostResponseDto readPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.readPost(id);
        return postResponseDto;
    }


    @ApiOperation(
            value = "게시글 삭제"
            , notes = "게시글 번호를 선택해, 선택 게시글을 삭제 합니다.")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<PostDeleteResponseDto> deletePost(@PathVariable Long id, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {


        PostDeleteResponseDto postDeleteResponseDto = postService.deletePost(id, userDetails.getUser());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json",
                Charset.forName("UTF-8")));

        return new ResponseEntity<>(postDeleteResponseDto, headers, StatusCode.OK);

    }

    @ApiOperation(
            value = "게시글 수정"
            , notes = "JSON 형식의 게시글 데이터를 받아 게시글을 수정합니다.")
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                                      @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostResponseDto postResponseDto = postService.updatePost(id, requestDto, userDetails.getUser());


        return postResponseDto;
    }


}
