package com.example.testtoy.domain.board;

import com.example.testtoy.domain.BaseTimeEntity;
import com.example.testtoy.domain.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Board extends BaseTimeEntity {
    public static final int INCREMENT = 1;
    public static final int DECREMENT = -1;

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @Column(name = "member_id")
    private String memberid;

    @Column(name = "member_name")
    private String name;

    private String title;

    private String content;

    private String state;

    @ColumnDefault("0")
    private Integer likes;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ColumnDefault("0")
    private Integer visit;

    public static Board createBoardForComment(Long boardid){
        return Board.builder()
                .id(boardid)
                .build();
    }

    public void updateBoardState(String state){
        this.state = state;
    }

    public void updateBoardLikes(Integer likes){
        this.likes = likes;
    }

    public void increaseBoardVisit(){
        this.visit += INCREMENT;
    }

    public void increaseBoardLikes(){
        this.likes += INCREMENT;
    }

    public void decreaseBoardLikes(){
        this.likes += DECREMENT;
    }

}
