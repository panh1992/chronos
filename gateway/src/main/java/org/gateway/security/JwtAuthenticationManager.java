package org.gateway.security;

import org.core.util.JwtUtil;
import org.gateway.configuration.Properties;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * jwt方式验证器
 *
 * @author panhong
 */
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private Properties properties;

    public JwtAuthenticationManager(Properties properties) {
        this.properties = properties;
    }

    /**
     * 认证处理，返回一个Authentication的实现类则代表认证成功，返回null则代表认证失败
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication).filter(this::supports).switchIfEmpty(Mono.empty())
                .flatMap(authentication1 -> {
                    if (Objects.isNull(authentication1)) {
                        return Mono.error(new UsernameNotFoundException("token 不允许为空"));
                    }
                    try {
                        JwtClaims claims = JwtUtil.validation(authentication.getCredentials().toString(),
                                properties.getSecurity().getJwt().getMaxFutureValidityInMinutes());
                        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(claims.getSubject());
                        authenticationToken.setAuthenticated(true);
                        return Mono.just(authenticationToken);
                    } catch (InvalidJwtException | MalformedClaimException e) {
                        return Mono.error(new BadCredentialsException("token 验证失败"));
                    }
                });
    }

    private boolean supports(Authentication authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

}
