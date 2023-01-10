package com.sparta.spring_deeping_week_assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class LikeComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMMENTS_ID", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public LikeComments(Comment comment, User user) {

        this.comment = comment;
        this.user = user;
    }

}
