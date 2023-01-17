package com.play.walk.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CourseMyWalkHistHeaderRtnVo {

    private String courseLengthSum;
    private String courseKcalSum;
    @Setter
    private List<CourseMyWalkHistRtnVo> courseMyWalkHistRtnVoList;

}
