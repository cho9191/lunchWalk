package com.play.walk.controller;

import com.play.walk.service.CourseService;
import com.play.walk.service.UserService;
import com.play.walk.vo.CourseDetlVo;
import com.play.walk.vo.CourseHVo;
import com.play.walk.vo.CourseRtnVo;
import com.play.walk.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/course")
@RequiredArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public UserVo createCourse(@RequestBody Map<String, String> params){

        String courseName = params.get("courseName");
        String courseLatitude = params.get("courseLatitude");
        String courseLongitude = params.get("courseLongitude");

        Double courseLength = Double.valueOf(params.get("courseLength"));
        Double courseKcal = Double.valueOf(params.get("courseKcal"));

        String courseImgUrl = params.get("courseImgUrl");

        System.out.println("courseName : "+courseName);
        System.out.println("courseLatitude : "+courseLatitude);
        System.out.println("courseLongitude : "+courseLongitude);
        System.out.println("courseLength : "+courseLength);
        System.out.println("courseKcal : "+courseKcal);
        System.out.println("courseImgUrl : "+courseImgUrl);

        courseService.createCourse(courseName, courseLatitude, courseLongitude, courseLength, courseKcal, courseImgUrl);
      return null;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public List<CourseRtnVo> searchCourse(@RequestBody Map<String, String> params){

        return courseService.searchCourse();
    }




    @RequestMapping(value = "/confirm", method = {RequestMethod.POST})
    public CourseRtnVo confirmCourse(@RequestBody Map<String, String> params){

        String isAutoYn = params.get("isAutoYn");

        System.out.println("isAutoYn : "+isAutoYn);
        return courseService.createCourse(isAutoYn);
    }

    /*
    * 임시 테스트 데이터 생성 영역
    *
    * */

    @RequestMapping(value = "/tmp/hist", method = {RequestMethod.POST})
    public UserVo createTmpCreateHist(@RequestBody Map<String, String> params){

        Integer courseId = Integer.valueOf(params.get("courseId"));

        courseService.createTmpCreateHist(courseId);
        return null;
    }

}
