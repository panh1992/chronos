package org.storage.configuration;

import org.core.exception.BusinessException;
import org.core.resp.ErrorResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理信息
 *
 * @author panhong
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> handle(BusinessException businessException) {
        return ResponseEntity.status(businessException.getStatus().getStatusCode())
                .body(ErrorResp.builder().build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResp exceptionHandle(Exception e) {
        e.printStackTrace();
        return ErrorResp.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("服务内部错误").build();
    }

}
