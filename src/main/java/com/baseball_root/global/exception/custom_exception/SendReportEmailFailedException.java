package com.baseball_root.global.exception.custom_exception;


import com.baseball_root.global.exception.CustomException;
import com.baseball_root.global.response.ErrorCode;

public class SendReportEmailFailedException extends CustomException {
    public SendReportEmailFailedException() {
        super(ErrorCode.SEND_REPORT_EMAIL_FAILED_EXCEPTION);
    }
}
