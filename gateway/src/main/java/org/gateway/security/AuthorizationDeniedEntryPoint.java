package org.gateway.security;

import org.core.resp.ErrorResp;
import org.core.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 权限授权失败返回响应
 */
@Component
public class AuthorizationDeniedEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        String clientIp = Objects.requireNonNull(remoteAddress).getAddress().getHostAddress();
        ErrorResp errorResp = ErrorResp.builder().hostId(clientIp).requestId(exchange.getRequest().getId())
                .code(HttpStatus.UNAUTHORIZED.name()).message(denied.getMessage()).build();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(CommonUtil.toJson(errorResp)
                .getBytes(StandardCharsets.UTF_8))));
    }

}
