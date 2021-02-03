package com.vueblog.Shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author m
 * @version 1.0
 * @className JwtToken
 * @description JwtToken
 * @date 2021/1/31
 */
public class JwtToken implements AuthenticationToken {

    private final String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
