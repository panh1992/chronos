package org.core.exception;

import javax.ws.rs.core.Response;

/**
 * 远程接口访问异常
 */
public class RemoteAccessException extends BusinessException {

    private RemoteAccessException(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static RemoteAccessException build(String message) {
        return new RemoteAccessException(Response.Status.GATEWAY_TIMEOUT, "RemoteAccessFailed", message);
    }

    public static RemoteAccessException build(String code, String message) {
        return new RemoteAccessException(Response.Status.GATEWAY_TIMEOUT, code, message);
    }

    public static RemoteAccessException build(Response.Status status, String code, String message) {
        return new RemoteAccessException(status, code, message);
    }

}
