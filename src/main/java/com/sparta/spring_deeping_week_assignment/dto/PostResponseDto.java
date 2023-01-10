package com.sparta.spring_deeping_week_assignment.dto;

import com.sparta.spring_deeping_week_assignment.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String contents;

    private String username;

    private List<PostCommentResponseDto> comment;

    private int likes;



    // entity -> dto
    public PostResponseDto(Post post, List<PostCommentResponseDto> comment, int likes) {
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.username = post.getUser().getUsername();
        this.comment = comment;
        this.likes = likes;
    }

}
