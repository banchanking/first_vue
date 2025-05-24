package com.jpa.maria.spring.service;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey = "adfewfdgawerdsgawetfgsadgaweawgfwegwgwg2123";

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();

        // 1) 만료 시간 설정 (현재 시각 + 5분)
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);

        byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "H256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {

        if(token != null && !"".equals(token)) {
            try{
                byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e){
                // 만료됨
            } catch (JwtException e) {
                // 유효하지않음
            }

        }

        return null;
    }

    @Override
    public boolean isValidate(String token) {

        System.out.println("isValidate" + token);

        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {

        Claims claims = this.getClaims(token);

        if(claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }
        return 0;
    }
}
