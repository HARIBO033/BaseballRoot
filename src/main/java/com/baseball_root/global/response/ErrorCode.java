package com.baseball_root.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ALREADY_EXISTS_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    ALREADY_EXISTS_NICKNAME_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
    BINDING_EXCEPTION(HttpStatus.BAD_REQUEST, "Binding Exception"),
    EMAIL_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다."),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 Refresh Token 입니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 Access Token 입니다."),
    INVALID_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 Token 정보 입니다."),
    INVALID_MEMBER_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 유저 고유 번호(id) 입니다."),
    INVALID_POST_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 게시물 고유 번호(id) 입니다."),
    NOT_ENOUGH_PERMISSION_EXCEPTION(HttpStatus.FORBIDDEN, "권한이 부족합니다."),
    SAVE_ATTACHMENT_FAILED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패했습니다."),
    VERIFY_VERTIFICATION_CODE_FAILED(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다."),
    VERIFICATION_CODE_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "인증 번호가 생성되지 않았거나, 유효 시간이 만료되었습니다."),
    SEND_REPORT_EMAIL_FAILED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "신고/문의 이메일 전송에 실패했습니다."),
    INVALID_LINK_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "link-type을 입력해주세요."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.BAD_REQUEST, "권한이 부족합니다."),
    INVALID_INTEGRATED_LINK_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 통합 게시판 링크 ID 입니다."),
    INVALID_COMMENT_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 댓글 ID 입니다."),
    NOT_FOUND_PARENT_COMMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "부모 댓글을 찾을 수 없습니다."),
    INVALID_ATTACHMENT_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 이미지 ID입니다."),
    DELETE_ATTACHMENT_FAILED_EXCEPTION(HttpStatus.BAD_REQUEST, "이미지 파일 삭제에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}