package com.sparta.spring_deeping_week_assignment.dto;


import com.sparta.spring_deeping_week_assignment.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String title;
    private String contents;




    @Builder
    public PostRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;

    }


    // dto -> entity
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .contents(contents)
                .build();
    }

}
