package com.play.walk.service;

import com.play.walk.mapper.CourseMapper;
import com.play.walk.repository.CourseHRepository;
import com.play.walk.repository.CourseHistRepository;
import com.play.walk.vo.CourseHVo;
import com.play.walk.vo.CourseHistVo;
import com.play.walk.vo.CourseRtnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseHRepository courseHRepository;
    @Autowired
    private CourseHistRepository courseHistRepository;
    @Autowired
    private CourseMapper courseMapper;

    public int createCourse(String courseName, String courseLatitude, String courseLongitude
            , double courseLength, double courseKcal, String courseImgUrl){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = (String) principal;

        String[] arrCourseLatitude = courseLatitude.split(",");
        String[] arrCourseLongitude = courseLongitude.split(",");

        System.out.println("arrCourseLatitude.length : "+arrCourseLatitude.length);
        System.out.println("arrCourseLongitude.length : "+arrCourseLongitude.length);
        System.out.println("courseImgUrl : "+courseImgUrl);

        if(arrCourseLatitude.length == arrCourseLongitude.length){
            //throw new RuntimeException("exception 발생");
        }

        CourseHVo resultVo = courseHRepository.save(CourseHVo.builder()
                                                                                    .courseName(courseName)
                                                                                    .courseCreateUserId(userId)
                                                                                    .courseLength(courseLength)
                                                                                    .courseKcal(courseKcal)
                                                                                    .courseImgUrl(courseImgUrl)
                                                                                    .build());

        //이후 위도, 경도 정보 입력하는 detl 로직 추가할 것
        System.out.println("resultVo.getCourseId() : "+resultVo.getCourseId());

        return resultVo.getCourseId();
    }

    public List<CourseRtnVo> searchCourse(){

        return courseMapper.searchCourse();
    }


    public CourseRtnVo createCourse(String isAutoYn){

        if("Y".equalsIgnoreCase(isAutoYn)){

            List<CourseRtnVo> courseRtnVoList = courseMapper.searchCourse();
            //리스트 요소 섞기
            Collections.shuffle(courseRtnVoList);

            int randomIdxNum = randomIdxNum(courseRtnVoList.size()-1);

            return courseRtnVoList.get(randomIdxNum);

        }else{
            return null;
        }
    }

    public int randomIdxNum(int max){

        int min = 0;
        Random random = new Random();

        int randomVal = random.nextInt(max + min) + min;
        System.out.println("randomVal : "+randomVal);

        return randomVal;
    }



    public int createTmpCreateHist(Integer courseId){

        CourseHistVo vo = courseHistRepository.save(CourseHistVo.builder()
                                                                        .courseId(courseId)
                                                                        .build());

        System.out.println("vo.getId() : "+vo.getId());

        return 0;
    }



}
