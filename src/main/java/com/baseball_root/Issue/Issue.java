package com.baseball_root.Issue;

import com.baseball_root.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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


    public static Issue createIssue(Member sender, Member receiver, IssueType issueType) {
        return Issue.builder()
                .sender(sender)
                .receiver(receiver)
                .issueType(issueType)
                .isRead(false)
                .build();
    }
}
