package org.account.configuration;

import org.core.exception.BaseBusinessException;
import org.core.resp.ErrorResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<ErrorResp> handle(BaseBusinessException businessException) {
        return ResponseEntity.status(businessException.getStatus().getStatusCode())
                .body(ErrorResp.builder().build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResp exceptionHandle() {
        return ErrorResp.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("服务内部错误").build();
    }

}
