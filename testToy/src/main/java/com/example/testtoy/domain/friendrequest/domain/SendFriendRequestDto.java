package com.example.testtoy.domain.friendrequest.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendFriendRequestDto {

    private Long senderId;

    private Long receiverId;

}
