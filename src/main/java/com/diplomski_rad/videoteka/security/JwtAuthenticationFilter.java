package com.diplomski_rad.videoteka.security;

import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    FusionAuth fusionAuth;

    @Autowired
    Decoder decoder;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (UserService.jwtLoggedUser != null) {
            String jwt = UserService.jwtLoggedUser;

            if(fusionAuth.validateJwt(jwt)){
                    var ro = decoder.getRoles(jwt);
                    var username = decoder.getUsername(jwt);
                    List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
                for (String r: ro
                     ) {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(r));
                }

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username,
                                null, simpleGrantedAuthorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }else {
            Authentication authentication = null;
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

