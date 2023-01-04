package com.play.walk.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select id from test where 1=1 and id = #{id}")
    String abc(@Param("id") String id);


    @Select("select id from test where 1=1")
    List<String> abcList();


    @Insert("INSERT INTO test(id) VALUES(now())")
    int save();

}
