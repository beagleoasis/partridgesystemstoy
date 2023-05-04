package com.example.testtoy.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 생성
@Getter // Lombok getter 생성
@Entity // JPA Entity 명시
@Table(name = "user") // user 테이블
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_password")
    private String password;

    @Builder
    public User(String id, String password){
        this.id = id;
        this.password = password;
    }

}
