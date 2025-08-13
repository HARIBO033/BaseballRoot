package com.baseball_root.member;

import com.baseball_root.diary.domain.Diary;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Comparable<Member> {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "age", nullable = true)
    private Integer age;

    @Column(name = "nickname", nullable = true)
    private String nickname;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "favorite_team", nullable = true)
    private String favoriteTeam;

    //고유코드
    @Column(name = "member_code", nullable = false)
    private String memberCode;

    @Column(name = "naver_id", nullable = false)
    private String naverId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Member> friends = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;



    public void anonymizeAndSoftDelete() {
        this.name = "탈퇴한 사용자";
        this.nickname = "탈퇴한 사용자";
        this.profileImage = "탈퇴한 사용자";
        this.gender = "탈퇴한 사용자";
        this.memberCode = "탈퇴한 사용자";
        this.naverId = "탈퇴한 사용자";
        this.age = -1;
        this.favoriteTeam = "탈퇴한 사용자";
        this.deleted = true;
    }


    public String makeMemberCode() {
        CreateUuid createUuid = new CreateUuid();
        memberCode = createUuid.createUuid();
        return memberCode;
    }

    public MemberDto toDto() {
        return MemberDto.builder()
                .id(this.id)
                .nickname(this.nickname)
                .profileImage(this.profileImage)
                .build();
    }

    public void addFriend(Member friend) {
        this.friends.add(friend);
    }

    public void update(String profileImage, String nickname, String favoriteTeam) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.favoriteTeam = favoriteTeam;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // 가장 최근에 작성한 Diary 반환
    public Diary getLatestDiary() {
        return diaries.stream()
                .max(Comparator.comparing(Diary::getCreatedAt))
                .orElse(null);
    }

    // 정렬 기준: 가장 최근 Diary 작성일 내림차순
    @Override
    public int compareTo(Member other) {
        Diary myLatestDiary = this.getLatestDiary();
        Diary otherLatestDiary = other.getLatestDiary();

        if (myLatestDiary == null && otherLatestDiary == null) {
            return 0;
        }
        if (myLatestDiary == null) {
            return 1;
        }
        if (otherLatestDiary == null) {
            return -1;
        }
        return otherLatestDiary.getCreatedAt().compareTo(myLatestDiary.getCreatedAt());
    }

}
