package com.example.testtoy.domain.comment;

import com.example.testtoy.domain.board.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private String state;

    private int likes;

    public static Comment createComment(Board board, String name, String content, Long memberid){
        return Comment.builder()
                .board(board)
                .name(name)
                .content(content)
                .memberid(memberid)
                .build();
    }

}
