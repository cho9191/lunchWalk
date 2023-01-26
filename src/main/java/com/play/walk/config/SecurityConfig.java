package com.play.walk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler customSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler customFailureHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                . authorizeRequests()

                //antMatchers 설정한 리소스의 접근을 인증절차 없이 허용
                .antMatchers("/user/**", "/user**", "/login**","/web-resources/**", "/actuator/**").permitAll()
                //인증 후 ADMIN 레벨의 권한을 가진 사용자만 접근을 허용한다는
                //.antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/order/**").hasAnyRole("USER")

                //그외 나머지 리소스들은 무조건 인증을 완료해야 접근이 가능
                .anyRequest().authenticated()
                .and()
                //customAuthenticationProvider 등록
                //.authenticationProvider(this.authenticationProvider())
                // .authenticationManager()
                .formLogin()
                .loginPage("/login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/ttt")
                .defaultSuccessUrl("/main.html")
                .successHandler(customSuccessHandler)
                .failureHandler(customFailureHandler)
               ;
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
