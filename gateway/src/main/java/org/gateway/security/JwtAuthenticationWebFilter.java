package org.gateway.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    private String[] authPermitList;

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager,
                                      JwtAuthenticationConverter converter, String[] authPermitList) {
        super(authenticationManager);
        setServerAuthenticationConverter(converter);
        setRequiresAuthenticationMatcher(new JWTHeadersExchangeMatcher());
        this.authPermitList = authPermitList;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return super.filter(exchange.mutate().request(exchange.getRequest()).build(), chain);
    }

    class JWTHeadersExchangeMatcher implements ServerWebExchangeMatcher {

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
