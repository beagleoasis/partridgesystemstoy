package com.example.testtoy.domain.board;

import com.example.testtoy.domain.BaseTimeEntity;
import com.example.testtoy.domain.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board extends BaseTimeEntity {

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

    private int likes;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
