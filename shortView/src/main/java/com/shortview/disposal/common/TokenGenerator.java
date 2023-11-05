package com.shortview.disposal.common;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {

    private static final long EXPIRATION = 86400L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISS = "qiniuyun";
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";

    public static String generateToken(String username) {
        Map<String, Object> customerUserMap = new HashMap<>();
        customerUserMap.put("username", username);
        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date())
                .setIssuer(ISS)
                .setSubject(JSON.toJSONString(customerUserMap))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
        return TOKEN_PREFIX + jwtToken;
    }
}
