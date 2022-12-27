package com.token.authenticate.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    public static boolean isExpired(String token, String secretKey) { //Expired == 만료된
        // expired가 지금보다 전에 됐으면 true
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public static String getUserName(String token, String secretKey) { // JwtTokenFilter에서 꺼낼거야
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody()
                .get("userName", String.class); //userName을 String으로 꺼낸다.
    }

    public static String createToken(String userName, String key, Long expiredUtilMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredUtilMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
