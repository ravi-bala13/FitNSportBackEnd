package com.fitnsport.server.Components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccessTokenFilter extends OncePerRequestFilter {

    @Autowired
    AccessTokenParserHelper accessTokenParserHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader("Access-Token");
        if (accessToken != null) {
            accessTokenParserHelper.parseToken(accessToken);

            if(accessTokenParserHelper.isTokenExpired()){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid token");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            accessTokenParserHelper.clear(); // Clear ThreadLocal after request
        }
    }
}
