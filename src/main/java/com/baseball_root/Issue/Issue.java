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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(
            name = "fk_issue_sender_id",
            foreignKeyDefinition = "FOREIGN KEY (sender_id) REFERENCES member(id) ON DELETE CASCADE"
    ))
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(
            name = "fk_issue_receiver_id",
            foreignKeyDefinition = "FOREIGN KEY (receiver_id) REFERENCES member(id) ON DELETE CASCADE"
    ))
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
