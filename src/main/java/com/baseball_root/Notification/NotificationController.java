package com.baseball_root.Notification;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    //Last-Event-ID는 SSE 연결이 끊어졌을 경우, 클라이언트가 수신한 마지막 데이터의 id 값을 의미합니다. 항상 존재하는 것이 아니기 때문에 false
    //클라이언트의 이벤트 구독을 수락한다. text/event-stream은 SSE를 위한 Mime Type이다. 서버 -> 클라이언트로 이벤트를 보낼 수 있게된다.
    @GetMapping(value = "/subscribe/{memberId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable(name = "memberId") Long memberId,
                                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return ResponseEntity.ok(notificationService.subscribe(memberId, lastEventId));
    }

    /*@PostMapping("/broadcast/{memberId}")
    public void broadcast(@PathVariable Long memberId, @RequestBody EventPayload eventPayload) {
        sseEmitterService.broadcast(userId, eventPayload);
    }*/
}