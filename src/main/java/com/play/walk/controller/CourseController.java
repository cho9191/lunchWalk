package com.play.walk.controller;

import com.play.walk.service.CourseService;
import com.play.walk.service.UserService;
import com.play.walk.vo.*;
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

        courseService.createCourse(courseName, courseLatitude, courseLongitude, courseLength, courseKcal);
      return null;
    }

    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public List<CourseRtnVo> searchCourse(@RequestBody Map<String, String> params){

        return courseService.searchCourse();
    }

    @RequestMapping(value = "/confirm", method = {RequestMethod.POST})
    public CourseRtnVo confirmCourse(@RequestBody Map<String, String> params){

        String isAutoYn = params.get("isAutoYn");
        String courseId = params.get("courseId");

        log.info("isAutoYn : {}  courseId : {}", isAutoYn, courseId);
        return courseService.createCourse(isAutoYn, courseId);
    }


    @RequestMapping(value = "/today", method = {RequestMethod.POST})
    public CourseRtnVo todayCourse(){
        return courseService.todayCourse();
    }

    @RequestMapping(value = "/map/url", method = {RequestMethod.POST})
    public String mapUrl(@RequestBody Map<String, String> params){
        int courseId = Integer.parseInt(params.get("courseId"));
        return courseService.mapUrl(courseId);
    }

    @RequestMapping(value = "/walk/hist", method = {RequestMethod.POST})
    public List<CourseHistRtnVo> walkHist(){
        return courseService.walkHist();
    }


    @RequestMapping(value = "/cancel", method = {RequestMethod.POST})
    public int cancelCourse(){
        return courseService.cancelCourse();
    }

    @RequestMapping(value = "/hist/attend", method = {RequestMethod.POST})
    public int courseHistAttend(@RequestBody Map<String, String> params){
        String courseHistIds = params.get("courseHistIds");
        System.out.println("courseHistIds : "+courseHistIds);

        return courseService.courseHistAttend(courseHistIds);
    }

    @RequestMapping(value = "/my/walk/hist", method = {RequestMethod.POST})
    public CourseMyWalkHistHeaderRtnVo courseMyWalkHist(){
        return courseService.courseMyWalkHist();
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

    @RequestMapping(value = "/preview", method = {RequestMethod.POST})
    public String previewCourse(@RequestBody Map<String, String> params){

        String courseLatitude = params.get("courseLatitude");
        String courseLongitude = params.get("courseLongitude");

        return courseService.previewMapUrl(courseLatitude, courseLongitude);
    }

}
