package org.account.router;

import org.account.handler.RoleHandler;
import org.account.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
public class Router {

    /**
     * 账户路由
     *
     * @param userHandler 用户处理器
     */
    @Bean
    public RouterFunction<ServerResponse> accountRoutes(UserHandler userHandler) {
        return RouterFunctions.route(GET("/accounts").and(accept(APPLICATION_JSON)), userHandler::findAllUser)
                .andRoute(POST("/accounts").and(accept(APPLICATION_JSON)), userHandler::save)
                .andRoute(GET("/accounts/{user_id}").and(accept(APPLICATION_JSON)), userHandler::userInfo)
                .andRoute(GET("/auth/accounts/{username}").and(accept(APPLICATION_JSON)), userHandler::getByUsername);
    }

    /**
     * 角色路由
     *
     * @param roleHandler 角色处理器
     */
    @Bean
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler roleHandler) {
        return RouterFunctions.route(GET("/roles").and(accept(APPLICATION_JSON)), roleHandler::findAllRole)
                .andRoute(GET("/roles/{role_id}").and(accept(APPLICATION_JSON)), roleHandler::roleInfo);
    }

}
