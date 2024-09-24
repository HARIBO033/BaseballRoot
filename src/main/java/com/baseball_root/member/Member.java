package com.baseball_root.member;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "member")
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

    /*@Column(name = "profilePhoto", nullable = true)
    private String profilePhoto;

    @Column(name = "gender", nullable = false)
    private String gender;*/

    //고유코드
    /*@Column(name = "memberCode", nullable = false)
    private String memberCode;*/

    /*public String makeMemberCode() {
        this.memberCode = this.nickname + this.id;
        return memberCode;
    }*/


}
