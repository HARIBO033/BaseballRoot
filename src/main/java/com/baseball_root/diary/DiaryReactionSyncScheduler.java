package com.baseball_root.diary;

import com.baseball_root.diary.repository.CommentRepository;
import com.baseball_root.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiaryReactionSyncScheduler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final DiaryRepository diaryRepository;
    private final CommentRepository commentRepository;

    /**
     * 5분마다 비동기 좋아요 동기화
     */
    @Async
    @Scheduled(fixedRate = 300000)
    public void syncReactions() {
        log.info("Async reaction sync started");

        try {
            //다이어리 좋아요 sync
            try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection()
                    .scan(ScanOptions.scanOptions().match("diary:*:reactions").count(1000).build())) {

                cursor.forEachRemaining(rawKey -> {
                    String key = new String(rawKey);
                    Long diaryId = Long.parseLong(key.split(":")[1]);

                    if (!diaryRepository.existsById(diaryId)) {
                        redisTemplate.delete(key);
                        return;
                    }

                    Object value = redisTemplate.opsForValue().get(key);
                    long reactionCount = value != null ? Long.parseLong(value.toString()) : 0L;

                    diaryRepository.findById(diaryId).ifPresent(diary -> {
                        diary.setReactionCount((int) (diary.getReactionCount()+reactionCount));
                        diaryRepository.save(diary);
                        log.info("Diary {} reaction count synced: {}", diaryId, reactionCount);
                    });
                    redisTemplate.opsForValue().set(key, 0);
                });
            }
            // 댓글/대댓글 좋아요 sync
            try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection()
                    .scan(ScanOptions.scanOptions().match("comment:*:reactions").count(1000).build())) {

                cursor.forEachRemaining(rawKey -> {
                    String key = new String(rawKey);
                    Long commentId = Long.parseLong(key.split(":")[1]);

                    if (!commentRepository.existsById(commentId)) {
                        redisTemplate.delete(key);
                        return;
                    }

                    Object value = redisTemplate.opsForValue().get(key);
                    long reactionCount = value != null ? Long.parseLong(value.toString()) : 0L;

                    commentRepository.findById(commentId).ifPresent(comment -> {
                        comment.setReactionCount((int) (comment.getReactionCount() + reactionCount));
                        commentRepository.save(comment);
                        log.info("Comment {} reaction count synced: {}", commentId, reactionCount);
                    });
                    redisTemplate.opsForValue().set(key, 0);
                });
            }

            log.info("Async reaction sync completed");

        } catch (Exception e) {
            log.error("Error during async reaction sync", e);
        }
    }
}


