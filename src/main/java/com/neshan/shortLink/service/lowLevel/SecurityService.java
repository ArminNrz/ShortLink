package com.neshan.shortLink.service.lowLevel;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.neshan.shortLink.security.JwtHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {

    private final JwtHandler jwtHandler;

    public String getCustomerPhoneNumberByToken(String token) {
        token = token.substring("Bearer ".length());
        DecodedJWT decodedJWT = jwtHandler.getDecodedJWT(token);
        return decodedJWT.getSubject();
    }
}
