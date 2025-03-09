package com.baseball_root.Issue;

import com.baseball_root.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    private boolean isRead;
    //생성일
    @CreatedDate
    private LocalDateTime createdAt;


    public static Issue createIssue(Member sender, Member receiver, IssueType issueType) {
        return Issue.builder()
                .sender(sender)
                .receiver(receiver)
                .issueType(issueType)
                .isRead(false)
                .build();
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

}
