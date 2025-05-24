package com.jpa.maria.spring.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String getToken(String key, Object value);

    Claims getClaims(String token);

    boolean isValidate(String token);

    int getId(String token);


}
