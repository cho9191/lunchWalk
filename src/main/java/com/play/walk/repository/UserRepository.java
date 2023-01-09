package com.play.walk.repository;

import com.play.walk.vo.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserVo, Integer> {

    UserVo findByUserId(String userId);


}
