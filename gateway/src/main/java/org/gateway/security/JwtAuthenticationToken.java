package org.gateway.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@EqualsAndHashCode(callSuper = true)
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String credentials;

    public JwtAuthenticationToken(String jwtToken) {
        super(null);
        this.credentials = jwtToken;
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
