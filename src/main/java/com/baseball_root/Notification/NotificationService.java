package com.baseball_root.Notification;

import com.baseball_root.Issue.IssueType;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationService {

    // SSE 이벤트 타임아웃 시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final NotificationRepository notificationRepository;
    private final SseEmitterRepository sseRepository;
    private final MemberRepository memberRepository;

    /**
     * 클라이언트의 이벤트 구독을 허용하는 메서드
     */
    public SseEmitter subscribe(Long memberId, String lastEventId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));
        String emitterId = member.getId() + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = sseRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        //완료 시, 타임아웃 시, 에러 발생 시
        sseEmitter.onCompletion(() -> sseRepository.deleteById(emitterId));
        sseEmitter.onTimeout(() -> sseRepository.deleteById(emitterId));
        sseEmitter.onError((e) -> sseRepository.deleteById(emitterId));
        /* 503 Service Unavailable 방지용 dummy event 전송 */
        send(sseEmitter, emitterId, createDummyNotification(String.valueOf(member.getId())));

        /* client가 미수신한 event 목록이 존재하는 경우 */
        if(!lastEventId.isEmpty()) { //client가 미수신한 event가 존재하는 경우 이를 전송하여 유실 예방
            Map<String, Object> eventCaches = sseRepository.findAllEventCacheStartWithByMemberId(String.valueOf(member.getId())); //id에 해당하는 eventCache 조회
            eventCaches.entrySet().stream() //미수신 상태인 event 목록 전송
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> emitEventToClient(sseEmitter, entry.getKey(), entry.getValue()));
        }

        return sseEmitter;
    }
    /**
     * [SSE 통신]notification type별 event 전송
     */
    private void send(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            log.info("Sending SSE+ event: id={}, data={}", emitterId, data);
            sseEmitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("SSE+")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            log.error("Failed to send SSE event: id={}, error={}", emitterId, exception.getMessage());
            sseRepository.deleteById(emitterId);
            sseEmitter.completeWithError(exception);
        }
    }
    /**
     * [SSE 통신]dummy data 생성
     * : 503 Service Unavailable 방지
     */
    private Notifications createDummyNotification(String receiver) {
        return Notifications.builder()
                .notificationId(receiver + "_" + System.currentTimeMillis())
                .receiver(receiver)
                .content("send dummy data to client.")
                .notificationType(IssueType.DUMMY)
                .message(null)
                .readYn('N')
                .deletedYn('N')
                .build();
    }

    /**
     * [SSE 통신]specific user에게 알림 전송
     * 이벤트 발생시 생성된 send
     */
    public void send(String receiver, String content, IssueType type, String message) {
        //receiver는 memberId만 가지고있음
        Notifications notification = createNotification(receiver, content, type, message);
        /* 로그인한 client의 sseEmitter 전체 호출 */
        Map<String, SseEmitter> sseEmitters = sseRepository.findAllEmitterStartWithByMemberId(receiver);
        sseEmitters.forEach(
                (key, sseEmitter) -> {
                    log.info("@@@ key, notification, sseEmitter : {}, {}, {}", key, notification, sseEmitter.toString());
                    sseRepository.saveEventCache(key, notification); //저장
                    emitEventToClient(sseEmitter, key, notification); //전송
                }
        );
    }


    /**
     * [SSE 통신]notification type별 data 생성
     */
    @Transactional
    public Notifications createNotification(String receiver, String content, IssueType type, String message) {
        if(type.equals(IssueType.COMMENT)) { //댓글
            Notifications notifications = Notifications.builder()
                    .notificationId(receiver + "_" + System.currentTimeMillis())
                    .receiver(receiver)
                    .content(content)
                    .notificationType(IssueType.COMMENT)
                    .message(message)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            return notificationRepository.save(notifications);
        } else if(type.equals(IssueType.DIARY_REACTION)) { //다이어리 좋아요
            Notifications notifications = Notifications.builder()
                    .notificationId(receiver + "_" + System.currentTimeMillis())
                    .receiver(receiver)
                    .content(content)
                    .notificationType(IssueType.DIARY_REACTION)
                    .message(message)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            return notificationRepository.save(notifications);
        } else if(type.equals(IssueType.COMMENT_REACTION)) { // 댓글 좋아요
            Notifications notifications = Notifications.builder()
                    .notificationId(receiver + "_" + System.currentTimeMillis())
                    .receiver(receiver)
                    .content(content)
                    .notificationType(IssueType.COMMENT_REACTION)
                    .message(message)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            return notificationRepository.save(notifications);
        } else if(type.equals(IssueType.FOLLOW_REQUEST)) { // 팔ㄹㅗ우 요청
            Notifications notifications = Notifications.builder()
                    .notificationId(receiver + "_" + System.currentTimeMillis())
                    .receiver(receiver)
                    .content(content)
                    .notificationType(IssueType.FOLLOW_REQUEST)
                    .message(message)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            return notificationRepository.save(notifications);
        } else if(type.equals(IssueType.FOLLOW_ACCEPTED)) { //팔로우 수락
            Notifications notifications = Notifications.builder()
                    .notificationId(receiver + "_" + System.currentTimeMillis())
                    .receiver(receiver)
                    .content(content)
                    .notificationType(IssueType.FOLLOW_ACCEPTED)
                    .message(message)
                    .readYn('N')
                    .deletedYn('N')
                    .build();
            return notificationRepository.save(notifications);
        } else {
            return null;
        }
    }


    /**
     * [SSE 통신]
     */
    private void emitEventToClient(SseEmitter sseEmitter, String emitterId, Object data) {
        try {
            send(sseEmitter, emitterId, data);
        } catch (Exception e) {
            sseRepository.deleteById(emitterId);
            throw new RuntimeException("Connection Failed.");
        }
    }

}
