package com.example.testtoy.domain.commentlike.domain;

import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentlike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static CommentLike createCommentLike(Comment comment, Member member){
        return CommentLike.builder()
                .comment(comment)
                .member(member)
                .build();
    }

}
