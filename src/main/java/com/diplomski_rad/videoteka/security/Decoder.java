package com.diplomski_rad.videoteka.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class Decoder {

    @Value("${fusionauth.jwt.secret}")
    private String secretKey; // iz fa keyMaster

    public List<String> getRoles(String jwt) {
            log.info("JWT");
            log.info(jwt);
            log.info("Secret");
            log.info(secretKey);

            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("roles", List.class);
        }

        public String getUsername(String jwt) {
            log.info("JWT");
            log.info(jwt);
            log.info("Secret");
            log.info(secretKey);
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("preferred_username").toString();
        }


}
