package com.example.demo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    TEST_EMPTY_COMMENT(false, HttpStatus.BAD_REQUEST.value(), "코멘트를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_INVALID_LENGTH_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일의 길이는 20자 이하여야합니다."),
    POST_USERS_INVALID_LENGTH_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호의 길이는 6자 이상 20자 이하여야합니다."),
    POST_USERS_EXISTS_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    POST_TEST_EXISTS_MEMO(false, HttpStatus.BAD_REQUEST.value(), "중복된 메모입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    INVALID_MEMO(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 메모입니다."),
    FAILED_TO_LOGIN(false, HttpStatus.NOT_FOUND.value(), "없는 아이디거나 비밀번호가 틀렸습니다."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, HttpStatus.FORBIDDEN.value(), "권한이 없는 유저의 접근입니다."),
    NOT_EXIST_USER(false, HttpStatus.NOT_FOUND.value(), "일치하는 유저가 없습니다."),
    DORMANCY_USER(false, HttpStatus.UNAUTHORIZED.value(), "휴면계정입니다."),
    BLOCKED_USER(false, HttpStatus.UNAUTHORIZED.value(), "차단된 계정입니다."),
    INVALID_OAUTH_TYPE(false, HttpStatus.BAD_REQUEST.value(), "알 수 없는 소셜 로그인 형식입니다."),
    NOT_EXIST_ORDER(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 주문입니다."),
    NOT_EXIST_PRODUCT(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 상품입니다."),
    VALIDATION_FAILED(false, HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다"),
    INVALID_PRICE(false, HttpStatus.BAD_REQUEST.value(), "상품 가격이 일치하지 않습니다"),

    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),

    CANCEL_FAIL_PAYMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "결제 취소 실패"),

    MODIFY_FAIL_USERNAME(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "메모 수정 실패"),
    GET_FAIL_PORTONE_ACCESS_TOKEN(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "포트원 엑세스토큰을 받아오지 못했습니다"),
    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다."),
    GET_FAIL_PORTONE_PAYMENTS(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "결제 정보를 받아오지 못했습니다"),
    GET_FAIL_PORTONE_BILLING_KEY(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "포트원 빌링키를 받아오지 못했습니다"),
    SCHEDULE_FAIL_PAYMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "정기결제 등록에 실패했습니다"),
    GET_FAIL_PORTONE_PAYMENT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "포트원 서버에서 결제정보를 받아오지 못했습니다");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
