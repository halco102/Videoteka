package com.diplomski_rad.videoteka.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Decoder {


    private static String  secretKey = "Njk4N2U1NzkyNGM1OWIzN2M1YzViZGQ5NTRkYmI3MmM="; // iz fa keyMaster

        public static List<String> getRoles(String jwt) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // ako nije utf_8 il error, pa biraj
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("roles", List.class);
        }

        public static String getUsername(String jwt) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // ako nije utf_8 il error, pa biraj
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.get("preferred_username").toString();
        }


}
