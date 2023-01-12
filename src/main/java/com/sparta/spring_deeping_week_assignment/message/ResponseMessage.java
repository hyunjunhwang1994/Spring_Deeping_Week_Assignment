package com.sparta.spring_deeping_week_assignment.message;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String CREATED_USER_FAIL_ALREADY_EXISTS = "회원 가입 실패 / 이미 존재하는 아이디 입니다.";
    public static final String CREATED_USER_FAIL_USERNAME_FAIL = "회원 가입 실패 / 아이디는 최소 4자 이상, 10자 이하 / 알파벳 소문자, 숫자를 포함해야 합니다.";
    public static final String CREATED_USER_FAIL_PASSWORD_FAIL = "회원 가입 실패 / 비밀번호는 최소 8자 이상, 15자 이하 / 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다.";
//    public static final String UPDATE_USER = "회원 정보 수정 성공";
//    public static final String DELETE_USER = "회원 탈퇴 성공";
//    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
//    public static final String DB_ERROR = "데이터베이스 에러";

    public static final String POST_DELETE_SUCCESS = "글 삭제 성공";
    public static final String POST_DELETE_FAIL = "글 삭제 실패";

    public static final String CREATE_COMMENT_SUCCESS = "댓글 작성 성공";

    public static final String UPDATE_COMMENT_SUCCESS = "댓글 수정 성공";
    public static final String UPDATE_COMMENT_FAIL = "댓글 수정 실패";
    public static final String DELETE_COMMENT_SUCCESS = "댓글 삭제 성공";
    public static final String DELETE_COMMENT_FAIL = "댓글 삭제 실패";

    public static final String TOKEN_AUTH_ERROR = "토큰이 유효하지 않습니다.";
    public static final String USER_NOT_FOUND = "작성자만 삭제/수정 할 수 있습니다.";

    public static final String NOT_FOUND_POST = "해당 글(댓글)이 존재하지 않습니다.";

    public static final String CREATE_LIKE_SUCCESS = "좋아요 성공";
    public static final String CREATE_LIKE_FAIL = "좋아요 실패, 이미 좋아요를 누르셨습니다.";
    public static final String DELETE_LIKE_SUCCESS = "좋아요 해제 성공";

    public static final String DELETE_LIKE_FAIL = "좋아요 해제 실패, 좋아요를 누르지 않으셨습니다.";

}
