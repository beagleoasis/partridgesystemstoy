package com.example.testtoy.domain.board;

import com.example.testtoy.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String title;

    private String content;

    private String status;


}
