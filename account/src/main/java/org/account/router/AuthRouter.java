package org.account.router;

import org.account.handler.RoleHandler;
import org.account.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.Resource;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * 路由配置
 *
 * @author panhong
 */
@Configuration
public class AuthRouter {

    @Resource
    private UserHandler userHandler;

    @Resource
    private RoleHandler roleHandler;

    @Bean
    public RouterFunction<ServerResponse> peopleRoutes() {
        return RouterFunctions.route(GET("/accounts").and(accept(APPLICATION_JSON)), userHandler::findAllUser)
                .andRoute(POST("/accounts").and(accept(APPLICATION_JSON)), userHandler::save)
                .andRoute(GET("/accounts/{user_id}").and(accept(APPLICATION_JSON)), userHandler::userInfo)
                .andRoute(GET("/auth/accounts/{username}").and(accept(APPLICATION_JSON)), userHandler::getByUsername)
                .andRoute(GET("/roles").and(accept(APPLICATION_JSON)), roleHandler::findAllRole)
                .andRoute(GET("/roles/{role_id}").and(accept(APPLICATION_JSON)), roleHandler::roleInfo);
    }

}
