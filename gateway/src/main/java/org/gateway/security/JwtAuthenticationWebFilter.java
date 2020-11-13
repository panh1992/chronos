package org.gateway.security;

import org.core.util.Constant;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    private final String[] authPermitList;

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager, String[] authPermitList,
                                      ServerAuthenticationFailureHandler authenticationFailureHandler) {
        super(authenticationManager);
        this.authPermitList = authPermitList;
        setServerAuthenticationConverter(exchange -> Mono.just(new JwtAuthenticationToken(exchange
                .getRequest().getHeaders().getFirst(Constant.X_AUTHORIZATION_HEADER))));
        setRequiresAuthenticationMatcher(new JwtHeadersExchangeMatcher());
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    class JwtHeadersExchangeMatcher implements ServerWebExchangeMatcher {

        @Override
        public Mono<MatchResult> matches(final ServerWebExchange exchange) {
            // 跨域的预检请求
            if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
                return MatchResult.notMatch();
            }
            return ServerWebExchangeMatchers.pathMatchers(authPermitList).matches(exchange)
                    .flatMap(m -> m.isMatch() ? MatchResult.notMatch() : MatchResult.match());

        }

    }

}
