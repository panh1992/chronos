package org.account.handler;

import org.account.entity.User;
import org.account.service.UserService;
import org.core.params.UserParams;
import org.core.resp.UserRes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Instant;

@Component
public class UserHandler {

    @Resource
    private UserService userService;

    public Mono<ServerResponse> findAllUser(ServerRequest serverRequest) {
        System.out.println(serverRequest.exchange().getRequest().getHeaders().getFirst("X-user-id"));
        Flux<User> listFlux = Flux.fromIterable(userService.findAllUser());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(listFlux, User.class);
    }

    public Mono<ServerResponse> userInfo(ServerRequest serverRequest) {
        System.out.println(serverRequest.exchange().getRequest().getHeaders().getFirst("X-user-id"));
        Mono<User> userMono = Mono.just(userService.findUserById(Long
                .parseLong(serverRequest.pathVariable("user_id"))));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(userMono, User.class);
    }

    public Mono<ServerResponse> getByUsername(ServerRequest serverRequest) {
        String username = serverRequest.pathVariable("username");
        User user = userService.findByUsername(username);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(UserRes.builder().userId(user.getUserId()).username(user.getUsername())
                        .password(user.getPassword()).createTime(Instant.now()).build()), UserRes.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(serverRequest.bodyToMono(UserParams.class).flatMap(userParams -> {
                    userService.save(userParams);
                    return Mono.empty();
                }), Void.class);
    }

}
