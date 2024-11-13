package com.baseball_root.diary.domain;


import com.baseball_root.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Diary extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

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

    @Column(name = "lineUp", nullable = true)
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

    public Diary(String imageUrl, String home, String away,String place, String seat, String title, String content, String lineUp, String mvp, Member member) {
        this.imageUrl = imageUrl;
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


    public void update(String imageUrl, String seat, String title, String content, String lineUp, String mvp) {
        this.imageUrl = imageUrl;
        this.seat = seat;
        this.title = title;
        this.content = content;
        this.lineUp = lineUp;
        this.mvp = mvp;
    }
}
