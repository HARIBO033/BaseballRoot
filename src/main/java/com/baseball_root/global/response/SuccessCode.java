package com.baseball_root.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    REGISTER_MEMBER_SUCCESS(HttpStatus.CREATED, "회원 가입에 성공했습니다."),
    DELETE_MEMBER_SUCCESS(HttpStatus.ACCEPTED, "회원 탈퇴가 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    //회원가입먼저 해주세요
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다. 회원가입을 먼저 해주세요."),
    REFRESH_ACCESS_TOKEN_SUCCESS(HttpStatus.CREATED, "ACCESS TOKEN 재발급에 성공했습니다."),
    GET_POST_SUCCESS(HttpStatus.OK, "게시글 조회에 성공했습니다."),
    POST_POST_SUCCESS(HttpStatus.CREATED, "게시글 등록에 성공했습니다."),
    UPDATE_POST_SUCCESS(HttpStatus.ACCEPTED, "게시글 수정에 성공했습니다."),
    DELETE_POST_SUCCESS(HttpStatus.ACCEPTED, "게시글 삭제에 성공했습니다."),
    GET_POST_LIST_SUCCESS(HttpStatus.OK, "게시판 리스트 조회를 완료했습니다."),
    GET_MEMBERS_SUCCESS(HttpStatus.OK, "모든 회원 정보 조회에 성공했습니다."),
    GENERATE_VERIFICATION_CODE_SUCCESS(HttpStatus.CREATED, "이메일로 인증 번호를 발송하였습니다."),
    VERIFY_VERTIFICATION_CODE_SUCCESS(HttpStatus.ACCEPTED, "이메일 인증이 완료되었습니다."),
    SEND_REPORT_TO_ADMIN_SUCCESS(HttpStatus.ACCEPTED, "신고 메일을 관리자에게 발송했습니다."),
    UPDATE_MEMBER_ROLE_SUCCESS(HttpStatus.ACCEPTED, "회원 등급 변경에 성공했습니다."),
    UPDATE_ADMIN_INVENTORY_SUCCESS(HttpStatus.ACCEPTED, "관리자 메뉴 내용 변경에 성공했습니다."),
    GET_ADMIN_INVENTORY_SUCCESS(HttpStatus.OK, "관리자 메뉴 조회에 성공했습니다."),
    GET_LINK_LIST_SUCCESS(HttpStatus.OK, "관리자 링크 조회에 성공했습니다."),
    UPDATE_LINK_SUCCESS(HttpStatus.ACCEPTED, "관리자 링크 수정에 성공했습니다."),
    GET_INTEGRATED_LINK_LIST_SUCCESS(HttpStatus.OK, "통합 게시판 링크 조회에 성공했습니다."),
    UPDATE_INTEGRATED_LINK_SUCCESS(HttpStatus.ACCEPTED, "통합 게시판 링크 저장에 성공했습니다."),
    DELETE_INTEGRATED_LINK_SUCCESS(HttpStatus.ACCEPTED, "통합 게시판 링크 삭제에 성공했습니다."),
    GET_COALITION_LIST_SUCCESS(HttpStatus.OK, "제휴 업체 조회에 성공했습니다."),
    UPDATE_COALITION_SUCCESS(HttpStatus.ACCEPTED, "제휴 업체 저장에 성공했습니다."),
    DELETE_COALITION_SUCCESS(HttpStatus.ACCEPTED, "제휴 업체 삭제에 성공했습니다."),
    CHANGE_PASSWORD_SUCCESS(HttpStatus.ACCEPTED, "비밀 번호 변경에 성공했습니다."),
    CHANGE_NICKNAME_SUCCESS(HttpStatus.ACCEPTED, "닉네임 변경에 성공했습니다."),
    GET_COMMENT_SUCCESS(HttpStatus.OK, "댓글 조회에 성공했습니다."),
    UPDATE_COMMENT_SUCCESS(HttpStatus.ACCEPTED, "댓글 최신화에 성공했습니다."),
    DELETE_COMMENT_SUCCESS(HttpStatus.ACCEPTED, "댓글 삭제에 성공했습니다."),
    GET_MEMBER_SUCCESS(HttpStatus.OK, "회원 정보 조회에 성공했습니다."),
    SAVE_ATTACHMENT_SUCCESS(HttpStatus.OK, "이미지 저장에 성공했습니다."),
    DELETE_ATTACHMENT_SUCCESS(HttpStatus.ACCEPTED, "이미지 삭제에 성공했습니다."),
    GET_CRAWLING_SUCCESS(HttpStatus.OK, "크롤링 불러오기 성공했습니다"),
    UPDATE_REACTION_SUCCESS(HttpStatus.ACCEPTED, "리액션 최신화에 성공했습니다."),
    GET_FRIENDS_REQUEST_SUCCESS(HttpStatus.OK, "친구 요청 조회에 성공했습니다."),
    GET_ISSUE_LIST_SUCCESS(HttpStatus.OK, "이슈 리스트 조회에 성공했습니다."),
    SEND_FRIEND_REQUEST_SUCCESS(HttpStatus.CREATED, "친구 요청을 성공적으로 보냈습니다."),
    REQUEST_SUCCESS(HttpStatus.OK, "요청 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String reasonPhrase;

    SuccessCode(HttpStatus httpStatus, String reasonPhrase) {
        this.httpStatus = httpStatus;
        this.reasonPhrase = reasonPhrase;
    }
}