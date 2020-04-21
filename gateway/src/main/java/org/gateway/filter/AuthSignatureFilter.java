package org.gateway.filter;

import org.gateway.security.JwtAuthenticationToken;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 签名验证，请求头处理
 */
@Component
public class AuthSignatureFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(Objects::nonNull)
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> JwtAuthenticationToken.class.isAssignableFrom(authentication.getClass()))
                .map(authentication -> (JwtAuthenticationToken) authentication)
                .map(JwtAuthenticationToken::getCredentials)
                .flatMap(bearerToken -> {
                    // TODO: 此处进行签名验证, 网关进行请求头添加
                    ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                    builder.header("X-user-id", "1233434eewrdswww");
                    ServerHttpRequest request = builder.build();
                    return Mono.just(exchange.mutate().request(request).build());
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

}
