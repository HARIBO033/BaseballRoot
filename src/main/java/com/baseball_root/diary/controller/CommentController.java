package com.baseball_root.diary.controller;

import com.baseball_root.diary.domain.Comment;
import com.baseball_root.diary.dto.CommentDto;
import com.baseball_root.diary.service.CommentService;
import com.baseball_root.global.response.CommonResponse;
import com.baseball_root.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries/{diaryId}/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("")
    public CommonResponse<List<CommentDto.Response>> getCommentsByDiary(@PathVariable(name = "diaryId") Long diaryId) {
        log.info("getCommentsByDiary 호출 diaryId : {}", diaryId);
        // 댓글 및 대댓글 가져오기
        return CommonResponse.success(SuccessCode.GET_COMMENT_SUCCESS,commentService.getCommentsByDiary(diaryId));
    }

    @PostMapping("")
    public CommonResponse<?> createComment(@PathVariable("diaryId") Long diaryId, @RequestBody CommentDto.Request commentDto) {
        log.info("createComment 호출 request : {}", commentDto);
        commentService.createComment(diaryId, commentDto);
        return CommonResponse.success(SuccessCode.UPDATE_COMMENT_SUCCESS);
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<?> deleteComment(@PathVariable("commentId") Long commentId) {
        log.info("deleteComment 호출 delete id : {}", commentId);
        commentService.deleteComment(commentId);
        return CommonResponse.success(SuccessCode.DELETE_COMMENT_SUCCESS);
    }

}
