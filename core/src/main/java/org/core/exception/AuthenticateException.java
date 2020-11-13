package org.core.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 无权限异常
 *
 * @author panhong
 */
@Getter
public class AuthenticateException extends BaseBusinessException {

    private AuthenticateException(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static AuthenticateException build() {
        return new AuthenticateException(Response.Status.UNAUTHORIZED, "Unauthorized", "无权限访问");
    }

}
