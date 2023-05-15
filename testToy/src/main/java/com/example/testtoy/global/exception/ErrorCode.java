package com.example.testtoy.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"잘못된 요청" ),
    // 401 Unauthorized : 인증 실패
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 실패하였습니다."),
    // 403 Forbidden : 접근 권한이 없음
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    // 404 Not Found : 요청한 자원을 찾을 수 없음
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 결과가 없습니다."),
    BOARD_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 결과가 없습니다."),
    BOARD_LIKE_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 결과가 없습니다."),
    Member_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 결과가 없습니다."),
    FRIEND_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID에 해당하는 결과가 없습니다."),
    // 500 Internal Server Error : 서버 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류"),
    //
    CONFLICT(HttpStatus.CONFLICT, "Json parsing 에러."),
    // 502 Bad Gateway : 게이트웨이나 프록시 서버에서 잘못된 응답을 받음
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "OFFICE API NOT CONNECTED.");

    private final HttpStatus httpStatus;
    private final String detail;
}
