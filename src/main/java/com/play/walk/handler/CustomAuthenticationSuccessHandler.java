package com.play.walk.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("성공 이벤튼 탐");

        setDefaultTargetUrl("/admin/test/print");
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            // 인증 받기 전 url로 이동하기
            System.out.println("성공 이벤튼 탐1");
            //String targetUrl = savedRequest.getRedirectUrl();
            String targetUrl = "/main.html";
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            // 기본 url로 가도록 함
            System.out.println("성공 이벤튼 탐2");
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }

        //super.onAuthenticationSuccess(request, response, authentication);
    }
}
