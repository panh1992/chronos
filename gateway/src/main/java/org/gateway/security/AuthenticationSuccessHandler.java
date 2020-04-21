package org.gateway.security;

import org.core.resp.LoginResp;
import org.core.util.CommonUtil;
import org.core.util.JWTUtil;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        String jwtToken;
        try {
            jwtToken = JWTUtil.createToken(authentication.toString(), 120L);
        } catch (JoseException e) {
            return Mono.error(e);
        }
        LoginResp loginResp = LoginResp.builder().credentials(jwtToken).build();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(CommonUtil.toJson(loginResp)
                .getBytes(StandardCharsets.UTF_8))));

    }

}
