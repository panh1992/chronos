package org.account.handler;

import org.account.service.AuthService;
import org.core.params.UserParams;
import org.core.resp.LoginResp;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class AuthHandler {

    @Resource
    private AuthService authService;

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        Mono<UserParams> userParamsMono = serverRequest.bodyToMono(UserParams.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userParamsMono.handle((userParams, sink) -> {
                    sink.next(LoginResp.builder().credentials(authService
                            .login(userParams.getUsername(), userParams.getPassword())).build());
                    sink.complete();
                }), LoginResp.class);
    }

}
