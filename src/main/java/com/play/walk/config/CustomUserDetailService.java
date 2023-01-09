package com.play.walk.config;

import com.play.walk.repository.UserRepository;
import com.play.walk.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        System.out.println("loadUserByUsername Test!");
        UserVo userVo = userRepository.findByUserId(userId);

        if (userVo != null) {
            return new User(userVo.getUserName(), userVo.getUserPassword(), AuthorityUtils.createAuthorityList(userVo.getUserRole()));
        }
        throw new UsernameNotFoundException("Not found userId : " + userId);
    }

}
