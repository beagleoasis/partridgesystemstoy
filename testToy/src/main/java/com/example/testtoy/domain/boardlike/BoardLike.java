package com.example.testtoy.domain.boardlike;

import com.example.testtoy.domain.BaseTimeEntity;
import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.BoardRepository;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardlike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static BoardLike createBoardLike(Board board, Member member){
        return BoardLike.builder()
                .board(board)
                .member(member)
                .build();
    }

}
