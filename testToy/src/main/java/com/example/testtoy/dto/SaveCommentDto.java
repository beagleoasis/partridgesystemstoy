package com.example.testtoy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCommentDto {

    private Long board_id;

    private Long memberid;

    private String content;
}
