package com.example.testtoy.domain.comment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCommentDto {

    private Long board_id;

    private Long memberid;

    private String name;

    private String content;
}
