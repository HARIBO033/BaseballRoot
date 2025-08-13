package com.baseball_root.friend;

import com.baseball_root.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FriendManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_sender_id", foreignKeyDefinition = "FOREIGN KEY (sender_id) REFERENCES member(id) ON DELETE CASCADE"))
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", foreignKey = @ForeignKey(
            name = "fk_receiver_id",
            foreignKeyDefinition = "FOREIGN KEY (receiver_id) REFERENCES member(id) ON DELETE CASCADE"
    ))
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public FriendManagementDto toDto() {
        return FriendManagementDto.builder()
                .id(this.id)
                .senderName(this.sender.getName())
                .senderNickname(this.sender.getNickname())
                .senderProfileImage(this.sender.getProfileImage())
                .senderId(this.sender.getId())
                .receiverId(this.receiver.getId())
                .status(this.status)
                .build();
    }
}
