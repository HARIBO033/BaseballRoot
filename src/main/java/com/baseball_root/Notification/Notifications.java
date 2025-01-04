package com.baseball_root.Notification;

import com.baseball_root.Issue.IssueType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = "receiver")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_id")
    private String notificationId;

    //@Embedded
    @Column(name = "content", insertable=false, updatable=false)
    private String content;

    //@Embedded
    @Column(name = "url")
    private String message;

    @Column(name = "to_name")
    private String toName;		// 대상

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private IssueType notificationType;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "read_yn")
    private char readYn;

    @Column(name = "deleted_yn")
    private char deletedYn;

}