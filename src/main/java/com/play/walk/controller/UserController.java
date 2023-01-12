package com.play.walk.controller;

import com.play.walk.repository.UserRepository;
import com.play.walk.service.UserService;
import com.play.walk.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/info", method = {RequestMethod.POST})
    public UserVo getUserInfo(Authentication authentication){
      log.info("getUserInfo Start!");
        String userId = (String) authentication.getPrincipal();
      return userService.getUserInfo(userId);
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public void createUser(@RequestBody UserVo userVo) throws Exception {
        log.info("createUser Start! ");
        log.info("getUserId : "+userVo.getUserId());
        log.info("getUserName : "+userVo.getUserName());
        log.info("password : "+userVo.getUserPassword());

        userService.createUser(userVo);
    }

    @RequestMapping(value = "/info/name", method = {RequestMethod.GET})
    public void getUserName(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        System.out.println("username : "+username);


    }


}
