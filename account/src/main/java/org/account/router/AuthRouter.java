package org.account.router;

import org.account.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AuthRouter {

    @Bean
    public RouterFunction<ServerResponse> peopleRoutes(AuthHandler authHandler) {
        return RouterFunctions.route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login);
    }

}
