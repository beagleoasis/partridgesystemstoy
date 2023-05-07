package com.example.testtoy.domain.friend;

import com.example.testtoy.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "member1_id")
    private Member member1;

    @ManyToOne
    @JoinColumn(name = "member2_id")
    private Member member2;


}
