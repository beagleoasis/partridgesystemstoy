package com.example.testtoy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendFriendRequestDto {

    private Long senderId;

    private Long receiverId;

}
