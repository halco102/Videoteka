package com.diplomski_rad.videoteka.security;

import com.diplomski_rad.videoteka.repository.person.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserPrincipal up = null;

        Set<String> roles = new TreeSet<>();
        var user = userRepository.checkIfUserExists(s);

        if (user.isPresent()) {

            for (String r: user.get().getRoles()
                 ) {
                roles.add(r);
            }

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            for (String rola : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(rola));
            }


            List<GrantedAuthority> authorities = new ArrayList<>(grantedAuthorities);

            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            up = new UserPrincipal(user.get().getUsername(), user.get().getPassword(), authorities);

            return up;

        }

        return null;
    }

}
