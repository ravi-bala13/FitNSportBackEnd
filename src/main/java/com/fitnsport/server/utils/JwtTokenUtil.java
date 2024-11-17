package com.fitnsport.server.utils;

import com.fitnsport.server.Requests.CustomerBaseRequest;
import com.fitnsport.server.database.entity.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenUtil {
    private String secretKey = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
    public String generateToken(Customer user) {
        return Jwts.builder()
                .setSubject(user.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Token expires in 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
