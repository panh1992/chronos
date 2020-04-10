package org.gateway.configuration;

import org.gateway.security.JwtAuthenticationConverter;
import org.gateway.security.JwtAuthenticationWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

import javax.annotation.Resource;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/auth/login",
            "/auth/logout",
            "/actuator/health",
            "/api/socket/**"
    };

    @Resource
    private ReactiveAuthenticationManager authenticationManager;

    @Resource
    private JwtAuthenticationConverter authenticationConverter;

    @Resource
    private ServerAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private ServerAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ServerSecurityContextRepository serverSecurityContextRepository() {
        return NoOpServerSecurityContextRepository.getInstance();
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        return http.securityContextRepository(serverSecurityContextRepository()).cors().disable()
                .httpBasic().disable().logout().disable().authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .and()
                .addFilterAt(new JwtAuthenticationWebFilter(authenticationManager, authenticationConverter,
                        excludedAuthPages), SecurityWebFiltersOrder.AUTHORIZATION)
                .formLogin().loginPage("/login").authenticationEntryPoint(authenticationEntryPoint)
                .authenticationSuccessHandler(authenticationSuccessHandler)
                .authenticationFailureHandler(authenticationFailureHandler)
                .and().authorizeExchange()
                .anyExchange().authenticated().and().build();
    }

}
