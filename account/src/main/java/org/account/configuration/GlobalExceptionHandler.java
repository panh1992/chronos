package org.account.configuration;

import org.core.exception.BusinessException;
import org.core.resp.ErrorResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ErrorResp exceptionHandle(Exception ex) {
        return null;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> handle(BusinessException businessException) {
        return ResponseEntity.status(businessException.getStatus().getStatusCode())
                .body(ErrorResp.builder().build());
    }

}
