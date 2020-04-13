package org.gateway.security;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

public class UserAuthenticationWebFilter extends AuthenticationWebFilter {


    public UserAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setServerAuthenticationConverter(exchange -> {
            if (exchange.getRequest().getURI().getPath().equals("/login")
                    && HttpMethod.POST.equals(exchange.getRequest().getMethod())) {
                AtomicReference<JsonNode> node = null;
                exchange.getRequest().getBody().subscribe(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
                    DataBufferUtils.release(buffer);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        node.set(mapper.readTree(new String(bytes, StandardCharsets.UTF_8)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JsonNode json = node.get();
                    Mono.just(new UsernamePasswordAuthenticationToken(json.get("username").getTextValue(),
                            json.get("password").getTextValue()));
                });
            }
            return Mono.empty();
        });
        setRequiresAuthenticationMatcher(new UserExchangeMatcher());
    }

    @Override
    public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
        super.setSecurityContextRepository(new ServerSecurityContextRepository() {
            @Override
            public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
                return null;
            }

            @Override
            public Mono<SecurityContext> load(ServerWebExchange exchange) {
                return null;
            }
        });
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return super.filter(exchange.mutate().request(exchange.getRequest()).build(), chain);
    }

    class UserExchangeMatcher implements ServerWebExchangeMatcher {

        @Override
        public Mono<MatchResult> matches(final ServerWebExchange exchange) {
            // 跨域的预检请求
            if (CorsUtils.isPreFlightRequest(exchange.getRequest())) {
                return MatchResult.notMatch();
            }
            return ServerWebExchangeMatchers.pathMatchers("/auth/login").matches(exchange)
                    .flatMap(m -> m.isMatch() ? MatchResult.notMatch() : MatchResult.match());

        }
    }

}
