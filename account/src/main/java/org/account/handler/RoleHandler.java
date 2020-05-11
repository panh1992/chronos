package org.account.handler;

import org.account.entity.Role;
import org.account.entity.User;
import org.account.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class RoleHandler {

    @Resource
    private RoleService roleService;

    public Mono<ServerResponse> findAllRole(ServerRequest serverRequest) {
        Flux<Role> listFlux = Flux.fromIterable(roleService.findAllRole());
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(listFlux, Role.class);
    }

    public Mono<ServerResponse> roleInfo(ServerRequest serverRequest) {
        Mono<Role> roleMono = Mono.just(roleService.findRoleById(Long.parseLong(serverRequest
                .pathVariable("role_id"))));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(roleMono, Role.class);
    }

}
