package org.gateway.security;

import org.core.util.JWTUtil;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    /**
     * 认证处理，返回一个Authentication的实现类则代表认证成功，返回null则代表认证失败
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (this.supports(authentication)) {
            return Mono.just(authentication).filter(Objects::nonNull)
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("token 不允许为空")))
                    .flatMap(authentication1 -> {
                        try {
                            JwtClaims claims = JWTUtil.validation(authentication.getCredentials().toString(), 120);
                            UserAuthenticationToken authenticationToken = new UserAuthenticationToken(claims.getSubject());
                            authenticationToken.setAuthenticated(true);
                            return Mono.just(authenticationToken);
                        } catch (InvalidJwtException | MalformedClaimException e) {
                            return Mono.error(new BadCredentialsException("token 验证失败"));
                        }
                    });
        }
        return Mono.empty();
    }

    private boolean supports(Authentication authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

}
