package org.gateway.configuration;

import com.google.common.collect.Lists;
import org.core.resp.ErrorResp;
import org.core.util.CommonUtil;
import org.gateway.security.JwtAuthenticationManager;
import org.gateway.security.JwtAuthenticationWebFilter;
import org.gateway.security.UserAuthenticationWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Objects;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {"/auth/login", "/auth/logout", "/actuator/health",
            "/api/socket/**", "/login", "/auth/accounts/123"};

    @Resource
    private ReactiveAuthenticationManager userAuthenticationManager;

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
    ReactiveUserDetailsService userDetailsService() {
        UserDetails userDetails = new User("panhong", passwordEncoder().encode("123456"),
                Lists.newArrayList());
        return new MapReactiveUserDetailsService(userDetails);
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        return http.securityContextRepository(serverSecurityContextRepository()).httpBasic().disable()
                .csrf().disable().logout().disable().formLogin().disable()
                .addFilterAt(new UserAuthenticationWebFilter(userAuthenticationManager, authenticationSuccessHandler,
                        authenticationFailureHandler), SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterAt(new JwtAuthenticationWebFilter(userAuthenticationManager, excludedAuthPages,
                                authenticationFailureHandler), SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll() // option 请求默认放行
                .pathMatchers(excludedAuthPages).permitAll() // 无需进行权限过滤的请求路径
                .anyExchange().access((authentication, authorizationContext) -> {
                    // TODO: 此处做权限校验
                    return Mono.just(new AuthorizationDecision(true));
                }).and()
                .exceptionHandling().authenticationEntryPoint((exchange, denied) -> {
                    ServerHttpResponse response = exchange.getResponse();


                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
                    String clientIp = Objects.requireNonNull(remoteAddress).getAddress().getHostAddress();
                    ErrorResp errorResp = ErrorResp.builder().hostId(clientIp).requestId(exchange.getRequest().getId())
                            .code(HttpStatus.UNAUTHORIZED.name()).message(denied.getMessage()).build();
                    return response.writeWith(Mono.just(response.bufferFactory().wrap(CommonUtil.toJson(errorResp)
                            .getBytes(StandardCharsets.UTF_8))));
                }).and().build();
    }

}
