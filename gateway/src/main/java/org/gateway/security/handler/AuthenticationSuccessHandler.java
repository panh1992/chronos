package org.gateway.security.handler;

import org.core.resp.LoginResp;
import org.core.util.CommonUtil;
import org.core.util.JwtUtil;
import org.gateway.configuration.Properties;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author panhong
 */
@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Resource
    private Properties properties;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {

        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        String jwtToken;
        try {
            jwtToken = JwtUtil.createToken(authentication.getName(), properties.getSecurity().getJwt()
                    .getExpirationTimeMinutes());
        } catch (JoseException e) {
            return Mono.error(e);
        }

        return response.writeWith(Mono.just(response.bufferFactory().wrap(CommonUtil.toJson(LoginResp.builder()
                .credentials(jwtToken).build()).getBytes(StandardCharsets.UTF_8))));

    }

}
