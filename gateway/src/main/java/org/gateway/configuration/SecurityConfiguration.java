package org.gateway.configuration;

import org.gateway.security.JwtAuthenticationManager;
import org.gateway.security.JwtAuthenticationWebFilter;
import org.gateway.security.UserAuthenticationWebFilter;
import org.gateway.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

import javax.annotation.Resource;
import java.util.LinkedList;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Resource
    private Properties properties;

    @Resource
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Resource
    private ReactiveAuthorizationManager<AuthorizationContext> authorizationManager;

    @Resource
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private ServerAuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private ServerAuthenticationEntryPoint authorizationDeniedEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ServerSecurityContextRepository serverSecurityContextRepository() {
        return NoOpServerSecurityContextRepository.getInstance();
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(new JwtAuthenticationManager());
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码
        // 不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
        UserDetailsRepositoryReactiveAuthenticationManager userDetailsRepositoryReactiveAuthenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService());
        userDetailsRepositoryReactiveAuthenticationManager.setPasswordEncoder(passwordEncoder());
        managers.add(userDetailsRepositoryReactiveAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return new UserDetailsService();
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        return http.securityContextRepository(serverSecurityContextRepository()).httpBasic().disable()
                .csrf().disable().logout().disable().formLogin().disable()
                .addFilterAt(new UserAuthenticationWebFilter(reactiveAuthenticationManager,
                        authenticationSuccessHandler, authenticationFailureHandler), SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterAt(new JwtAuthenticationWebFilter(reactiveAuthenticationManager, properties.getSecurity()
                        .getExcludedUri(), authenticationFailureHandler), SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange()
                // 对 option 请求默认放行
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                // 无需进行权限过滤的请求路径
                .pathMatchers(properties.getSecurity().getExcludedUri()).permitAll()
                .anyExchange().access(authorizationManager).and()
                .exceptionHandling().authenticationEntryPoint(authorizationDeniedEntryPoint)
                .and().build();
    }

}
