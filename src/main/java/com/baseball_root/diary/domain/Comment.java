package com.baseball_root.diary.domain;

import com.baseball_root.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;  // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;  // 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    @JsonIgnore
    private Diary diary;  // 댓글이 달린 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;// 부모 댓글


    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();  // 대댓글 리스트

    private Long reactionCount = 0L;  // 댓글의 반응 수

    private String writerName;  // 탈퇴후 댓글 작성자 이름

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }
    public void setParentComment(Comment comment) {
        this.parent = comment;
    }

    public void increaseReactionCount() {
        if (this.reactionCount == null) {
            this.reactionCount = 0L;  // 기본값 설정
        }
        this.reactionCount++;
    }

    public void decreaseReactionCount() {
        if (this.reactionCount == null || this.reactionCount == 0L) {
            throw new IllegalStateException("ReactionCount 는 0보다 작을 수 없습니다.");
        }
        this.reactionCount--;
    }

    public void nullCheck(){
        if(this.reactionCount == null){
            this.reactionCount = 0L;
        }
    }

    public void setReactionCount(Integer reactionCount) {
        this.reactionCount = reactionCount.longValue();
    }
}