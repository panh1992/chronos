package org.gateway.security;

import com.google.common.collect.Lists;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 用户名、密码 过滤器
 */
public class UserAuthenticationWebFilter extends AuthenticationWebFilter {

    public UserAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager,
                                       ServerAuthenticationSuccessHandler authenticationSuccessHandler,
                                       ServerAuthenticationFailureHandler authenticationFailureHandler) {
        super(authenticationManager);
        setServerAuthenticationConverter(exchange -> exchange.getRequest().getBody().single()
                .flatMap(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
                    DataBufferUtils.release(buffer);
                    JsonNode json;
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        // noinspection BlockingMethodInNonBlockingContext
                        json = mapper.readTree(bytes);
                    } catch (IOException e) {
                        return Mono.error(new BadCredentialsException("解析失败"));
                    }
                    return Mono.just(json);
                }).map(json -> new UsernamePasswordAuthenticationToken(json.get("username").getTextValue(),
                        json.get("password").getTextValue(), Lists.newArrayList())));
        setRequiresAuthenticationMatcher(new UserExchangeMatcher());
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    static class UserExchangeMatcher implements ServerWebExchangeMatcher {

        @Override
        public Mono<MatchResult> matches(final ServerWebExchange exchange) {
            // 跨域的预检请求
            if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
                return MatchResult.notMatch();
            }
            return ServerWebExchangeMatchers.pathMatchers("/login").matches(exchange).flatMap(m -> {
                ServerHttpRequest request = exchange.getRequest();
                if (HttpMethod.POST.equals(request.getMethod())
                        && MediaType.APPLICATION_JSON.equals(request.getHeaders().getContentType())
                        && m.isMatch()) {
                    return MatchResult.match();
                }
                return MatchResult.notMatch();
            });
        }

    }

}
