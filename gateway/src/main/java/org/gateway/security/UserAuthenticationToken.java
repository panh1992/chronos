package org.gateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 认证后返回认证方式处理
 */
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public UserAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

}
