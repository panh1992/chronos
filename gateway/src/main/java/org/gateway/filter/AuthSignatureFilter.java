package org.gateway.filter;

import org.gateway.security.UserAuthenticationToken;
import org.gateway.security.UserInfo;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 签名验证，请求头处理
 *
 * @author panhong
 */
@Component
public class AuthSignatureFilter implements GlobalFilter {

    @Resource
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext().filter(Objects::nonNull)
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> UserAuthenticationToken.class.isAssignableFrom(authentication.getClass()))
                .map(authentication -> (UserAuthenticationToken) authentication)
                .map(UserAuthenticationToken::getPrincipal)
                .map(principal -> {
                    // TODO: 此处进行签名验证, 网关进行请求头添加
                    UserInfo userInfo = (UserInfo) userDetailsService.findByUsername(principal).block();
                    ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                    builder.header("X-user-id", String.valueOf(userInfo.getUserId()));
                    ServerHttpRequest request = builder.build();
                    return exchange.mutate().request(request).build();
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

}
