package com.baseball_root.diary.domain;


import com.baseball_root.attach.AttachImage;
import com.baseball_root.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Diary extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachImage> attachImages;

    @Column(name = "home", nullable = false)
    private String home;

    @Column(name = "away", nullable = false)
    private String away;
    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "seat", nullable = true)
    private String seat;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "line_up", nullable = true)
    private String lineUp;

    @Column(name = "mvp", nullable = true)
    private String mvp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "comment")
    @OneToMany(fetch= FetchType.LAZY, mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "game_result", nullable = false)
    private String gameResult;

    @Column(name = "game_date", nullable = false)
    private String gameDate;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions = new ArrayList<>();

    @Builder.Default // Default 를 붙여주면 null 이 들어오지 않음!
    @Column(name = "reaction_count", nullable = false)
    private Long reactionCount = 0L;
    private String authorName; // 탈퇴 후 작성자 이름 유지용
    public Diary(String home, String away, String place, String seat, String title, String content, String lineUp, String mvp, Member member) {
        this.home = home;
        this.away = away;
        this.place = place;
        this.seat = seat;
        this.title = title;
        this.content = content;
        this.lineUp = lineUp;
        this.mvp = mvp;
        this.member = member;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void update(String seat, String title, String content, String lineUp, String mvp) {
        this.seat = seat;
        this.title = title;
        this.content = content;
        this.lineUp = lineUp;
        this.mvp = mvp;
    }

    /* Reaction Count 는 Redis 로 관리하기로 함
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
    }*/

    public void nullCheck(){
        if(this.reactionCount == null){
            this.reactionCount = 0L;
        }
    }
    public List<String> getAttachImagesUrl(Diary diary) {
        return attachImages.stream()
                .map(AttachImage::getUrl)
                .toList();
    }

    public void setReactionCount(Integer reactionCount) {
        this.reactionCount = Long.valueOf(reactionCount);
    }
}
