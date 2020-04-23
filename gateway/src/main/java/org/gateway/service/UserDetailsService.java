package org.gateway.service;

import org.gateway.configuration.SpringContext;
import org.gateway.feign.AuthFeign;
import org.gateway.security.UserInfo;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class UserDetailsService implements ReactiveUserDetailsService {

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(SpringContext.getBean(AuthFeign.class).getByUsername(username))
                .map(userRes -> UserInfo.builder().userId(userRes.getUserId())
                        .username(userRes.getUsername()).password(userRes.getPassword()).build());
    }

}
