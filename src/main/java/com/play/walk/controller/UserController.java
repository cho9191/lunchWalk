package com.play.walk.controller;

import com.play.walk.repository.UserRepository;
import com.play.walk.service.UserService;
import com.play.walk.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public void getUserInfo(){
      log.info("getUserInfo Start!");
      userService.getUserInfo();
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public void createUser(@RequestBody UserVo userVo) throws Exception {
        log.info("createUser Start! ");
        log.info("getUserId : "+userVo.getUserId());
        log.info("getUserName : "+userVo.getUserName());
        log.info("password : "+userVo.getUserPassword());

        userService.createUser(userVo);
    }


}
