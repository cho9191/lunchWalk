package com.play.walk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("authenticate 시작123");

        String userName = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.out.println("authenticate 시작 userName : "+userName);
        System.out.println("authenticate 시작 password : "+password);

        UserDetails user = customUserDetailService.loadUserByUsername(userName);

        if (user == null) {
            throw new BadCredentialsException("해당 아이디가 존재하지 않습니다." + userName);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀 번호가 틀렸습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userName, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //true로 설정 변경해야 동작함
        return true;
    }
}
