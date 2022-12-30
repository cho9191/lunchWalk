package com.play.walk.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("loadUserByUsername Test!");

        if ("user".equals(username)) {
            return new User(username, new BCryptPasswordEncoder().encode("1111"), AuthorityUtils.createAuthorityList("USER"));
        }

        throw new UsernameNotFoundException("not found : " + username);
    }

}
