package com.baseball_root.friend;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FriendManagementDto {

    private Long id;

    private String senderName;
    private Long receiverId;
    private FriendStatus status;

    public FriendManagementDto(String senderName, Long receiverId, FriendStatus status) {
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.status = status;
    }

}
