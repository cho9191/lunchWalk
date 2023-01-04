package com.play.walk.mapper;

import com.play.walk.vo.UserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER_INFO(" +
                    "ID" +
                    ",USER_ID" +
                    ",USER_NAME" +
                    ",USER_PASSWORD" +
                    ",USER_ROLE" +
                    ",USE_YN" +
                    ",INS_DTM" +
                ") " +
                "VALUES(" +
                    "#{id}" +
                    ",#{userId}" +
                    ",#{userName}" +
                    ",#{userPassword}" +
                    ",#{userRole}" +
                    ",'Y'" +
                    "now())")
    int createUser(UserVo userVo);

}
