package com.sparta.spring_deeping_week_assignment.entity;


import com.sparta.spring_deeping_week_assignment.dto.CommentsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "user")
    List<LikePost> likePosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<LikeComments> likeComments = new ArrayList<>();


    public Comment(CommentsRequestDto requestDto, User user, Post post) {

        this.contents = requestDto.getContents();
        this.user = user;
        this.post = post;
    }

    public void updateComment(CommentsRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }

}
