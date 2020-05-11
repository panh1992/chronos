package org.gateway.security;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 权限校验管理
 *
 * @author panhong
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Resource
    private PathMatcher pathMatcher;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication.filter(x -> UserAuthenticationToken.class.isAssignableFrom(x.getClass()))
                .map(userAuthenticationToken -> {
                    ServerHttpRequest request = context.getExchange().getRequest();
                    HttpMethod method = request.getMethod();
                    String path = request.getURI().getPath();
                    String username = ((UserAuthenticationToken) userAuthenticationToken).getPrincipal();
                    return new AuthorizationDecision(verification(username, path, method));
                }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 验证用户是否有访问权限
     *
     * @param username 用户名
     * @param path     访问路径
     * @param method   请求方式
     * @return true - 允许访问   false - 不允许访问
     */
    private boolean verification(String username, String path, HttpMethod method) {
        return !reactiveRedisTemplate.opsForSet().scan(username)
                .filter(uri -> uri.startsWith(method.name()) && pathMatcher.match(uri.substring(method.name()
                        .length()), path)).collectList().blockOptional().orElse(Lists.newArrayList()).isEmpty();
    }

}
