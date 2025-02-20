package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class NotEnoughPermissionException extends CustomException {
    public NotEnoughPermissionException() {
        super(ErrorCode.NOT_ENOUGH_PERMISSION_EXCEPTION);
    }
}
