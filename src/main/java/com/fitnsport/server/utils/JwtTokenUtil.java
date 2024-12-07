package com.fitnsport.server.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnsport.server.dto.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.ttl}")
    private int ttlForTokenExpiry = 60;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(AccessToken userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, AccessToken userDetails) {
        return buildToken(extraClaims, userDetails);
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            AccessToken userDetails
    ) {
        String userDetailsString;
        try {
            userDetailsString = objectMapper.writeValueAsString(userDetails);
        }catch (Exception ex){
            userDetailsString = "";
        }
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetailsString)
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public AccessToken extractAccessToken(String token) {
        String subject =  extractClaim(token, Claims::getSubject);
        try {
            return objectMapper.readValue(subject, AccessToken.class);
        }catch (Exception ex){
            return null;
        }
    }

    public boolean isTokenExpired(AccessToken token) {
        LocalDateTime tokenCreatedAt = token.getCreatedAt().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime tokenExpiryTime = tokenCreatedAt.plus(60, ChronoUnit.MINUTES);
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(tokenExpiryTime);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
