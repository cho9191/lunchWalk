package com.play.walk.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

@Getter
public class CourseRtnVo {
    private String courseId;
    private String courseName;
    private String courseCreateUserId;
    private String courseCreateUserName;
    private double courseLength;
    private double courseKcal;
    private int courseExecuteCnt;
    private int courseSatisfyPoint;
    private String courseInsDtm;

}
