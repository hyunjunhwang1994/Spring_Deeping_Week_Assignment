package com.sparta.spring_deeping_week_assignment.entity;

import com.sparta.spring_deeping_week_assignment.dto.PostRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(length = 300, nullable = false)
    private String contents;



    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, String title, String contents) {

        this.id = id;
        this.title = title;
        this.contents = contents;


    }

    public Post(PostRequestDto requestDto, User user) {

        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void updatePost(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
