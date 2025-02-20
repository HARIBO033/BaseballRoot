package com.baseball_root.global.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(SuccessCode successCode) {
        return new CommonResponse<>(successCode.getHttpStatus(), successCode.getReasonPhrase(), null);
    }

    public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
        return new CommonResponse<>(successCode.getHttpStatus(), successCode.getReasonPhrase(), data);
    }

    public static CommonResponse<?> error(ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus(), errorCode.getMessage(), null);
    }

    public static CommonResponse<?> error(HttpStatus httpStatus, String message) {
        return new CommonResponse<>(httpStatus, message, null);
    }
}