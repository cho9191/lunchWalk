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

        String userId = authentication.getName();
        String userPassword = (String) authentication.getCredentials();

        System.out.println("authenticate 시작 userId : "+userId);
        System.out.println("authenticate 시작 userPassword : "+userPassword);

        UserDetails user = customUserDetailService.loadUserByUsername(userId);

        if (user == null) {
            throw new BadCredentialsException("해당 아이디가 존재하지 않습니다." + userId);
        }

        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            System.out.println("비밀 번호가 틀렸습니다.");
            throw new BadCredentialsException("비밀 번호가 틀렸습니다.");
        }else{
            System.out.println("비밀 번호가 맞았습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userId, userPassword, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //true로 설정 변경해야 동작함
        return true;
    }
}
