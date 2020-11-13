package org.core.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 业务异常， 所有自定义异常需要继承此异常
 *
 * @author panhong
 */
@Getter
public abstract class BaseBusinessException extends RuntimeException {

    final Response.Status status;

    final String code;

    BaseBusinessException(Response.Status status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

}
