package com.example.testtoy.domain.comment.domain;

import com.example.testtoy.domain.board.domain.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Comment {
    public static final int INCREMENT = 1;
    public static final int DECREMENT = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberid;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String state;

    @Builder.Default
    private Integer likes = 0;

    public static Comment createComment(Board board, String name, String content, Long memberid){
        return Comment.builder()
                .board(board)
                .name(name)
                .content(content)
                .memberid(memberid)
                .build();
    }

    public void updateCommentState(String state){
        this.state = state;
    }

    public void increaseCommentLikes(){
        this.likes += INCREMENT;
    }

    public void decreaseCommentLikes(){
        this.likes += DECREMENT;
    }


}
