package com.play.walk.service;

import com.play.walk.repository.UserRepository;
import com.play.walk.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserVo getUserInfo(String userId){
        log.info("UserService Start~! getUserInfo!");

        return userRepository.findByUserId(userId);
    }

    public void createUser(UserVo userVo) throws Exception {
        log.info("UserService Start~! createUser!");
        UserVo vo = userVo;
        vo.setUserPassword(passwordEncoder.encode(userVo.getUserPassword()));
        try{
            userRepository.save(vo);
        }catch (Exception e){
            log.info("Exception 발생");
            log.info(e.getMessage());
            throw new Exception("test Exception start123!");
        }

    }

    public void userIdCheck(String userId){
        log.info("UserService Start~! createUser!");
        userRepository.findByUserId(userId);
    }

}
