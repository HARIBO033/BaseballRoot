package com.baseball_root.diary.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String imageUrl;

    @Column(name = "homeVsAway", nullable = false)
    private String homeVsAway;

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

    @Column(name = "author", nullable = false)
    private String author; //todo: 작성자 타입 객체로 변경

    /*@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private List<Comment> comment;*/

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Diary(String imageUrl, String homeVsAway, String place, String seat, String title, String content, String lineUp, String mvp, String author) {
        this.imageUrl = imageUrl;
        this.homeVsAway = homeVsAway;
        this.place = place;
        this.seat = seat;
        this.title = title;
        this.content = content;
        this.lineUp = lineUp;
        this.mvp = mvp;
        this.author = author;
    }

    public Diary fromEntity(String imageUrl, String homeVsAway, String place, String seat, String title, String content, String lineUp, String mvp, String author) {
        this.imageUrl = imageUrl;
        this.homeVsAway = homeVsAway;
        this.place = place;
        this.seat = seat;
        this.title = title;
        this.content = content;
        this.lineUp = lineUp;
        this.mvp = mvp;
        this.author = author;
        return this;
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
