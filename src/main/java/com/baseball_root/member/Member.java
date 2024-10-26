package com.baseball_root.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profilePhoto", nullable = true)
    private String profilePhoto;

    @Column(name = "gender", nullable = false)
    private String gender;

    //고유코드
    @Column(name = "memberCode", nullable = false)
    private String memberCode;

    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Member> friends = new ArrayList<>();
    public String makeMemberCode() {
        this.memberCode = this.nickname + this.id;
        return memberCode;
    }

    public MemberDto toDto(){
        return MemberDto.builder()
                .id(this.id)
                .nickname(this.nickname)
                .build();
    }

    public void addFriend(Member friend){
        this.friends.add(friend);
    }
}
