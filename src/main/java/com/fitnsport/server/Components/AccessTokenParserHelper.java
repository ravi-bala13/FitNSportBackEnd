package com.fitnsport.server.Components;

import com.fitnsport.server.dto.AccessToken;
import com.fitnsport.server.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenParserHelper {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public AccessToken accessTokenObj;

    private static final ThreadLocal<AccessToken> userDetailsThreadLocal = new ThreadLocal<>();

    public void parseToken(String accessToken) {
        accessTokenObj = jwtTokenUtil.extractAccessToken(accessToken);
    }

    public boolean isTokenExpired(){
        return jwtTokenUtil.isTokenExpired(accessTokenObj);
    }

    public void clear() {
        userDetailsThreadLocal.remove();
    }
}