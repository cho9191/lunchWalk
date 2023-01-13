package com.play.walk.service;

import com.play.walk.mapper.CourseMapper;
import com.play.walk.repository.CourseDetlRepository;
import com.play.walk.repository.CourseHRepository;
import com.play.walk.repository.CourseHistRepository;
import com.play.walk.vo.*;
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
    private CourseDetlRepository courseDetlRepository;
    @Autowired
    private CourseMapper courseMapper;

    public int createCourse(String courseName, String courseLatitude, String courseLongitude
            , double courseLength, double courseKcal, String courseImgUrl){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = (String) principal;

        String[] arrCourseLatitudes = courseLatitude.split(",");
        String[] arrCourseLongitudes = courseLongitude.split(",");


        if(arrCourseLatitudes.length != arrCourseLatitudes.length){
            //throw new RuntimeException("exception 발생");
        }

        CourseHVo resultVo = courseHRepository.save(CourseHVo.builder()
                                                                                    .courseName(courseName)
                                                                                    .courseCreateUserId(userId)
                                                                                    .courseLength(courseLength)
                                                                                    .courseKcal(courseKcal)
                                                                                    .courseImgUrl(courseImgUrl)
                                                                                    .build());

        if(arrCourseLatitudes.length>0){
            this.createCourseDetl(resultVo.getCourseId(), arrCourseLatitudes, arrCourseLongitudes);
        }
        return resultVo.getCourseId();
    }

    public void createCourseDetl(int courseId, String[] arrCourseLatitudes, String[] arrCourseLongitudes){

        for(int i=0; i<arrCourseLatitudes.length; i++){

            courseDetlRepository.save(CourseDetlVo.builder()
                    .courseId(courseId)
                    .courseLatitude(arrCourseLatitudes[i])
                    .courseLongitude(arrCourseLongitudes[i])
                    .seq(i)
                    .build());
        }

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

            CourseRtnVo rtnVo = courseRtnVoList.get(randomIdxNum);

            courseHistRepository.save(CourseHistVo.builder()
                    .courseId(rtnVo.getCourseId())
                     .autoYn("Y")
                    .build());

            return courseRtnVoList.get(randomIdxNum);

        }else{
            return null;
        }
    }

    public CourseRtnVo todayCourse(){

        CourseRtnVo rtnVo = courseMapper.todayCourse();

        return rtnVo;
    }

    public List<CourseHistRtnVo> walkHist(){
        return courseMapper.walkHist();
    }

    public int randomIdxNum(int max){

        int min = 0;
        Random random = new Random();

        int randomVal = random.nextInt(max + min) + min;
        System.out.println("randomVal : "+randomVal);

        return randomVal;
    }


    public String mapUrl(int courseId){

        String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=500&h=500";
        //String center = "&center=127.1054221,37.3591614";
        String center = "&center=127.1033938,37.4027288";
        String level = "&level=12";  //14
        //String marker = "&markers=type:d|size:mid|color:red|pos:127.1033938%2037.4027288";
        String key = "&X-NCP-APIGW-API-KEY-ID=wq1hwomit5";

        String marker = this.getMarker(courseId);
        System.out.println("marker : "+marker);

        StringBuilder sb = new StringBuilder();
        //sb.append(url).append(center).append(level).append(marker).append(key);

        return sb.append(url)
                        .append(center)
                        .append(level)
                        .append(marker)
                        .append(key)
                        .toString();
    }

    public String getMarker(int courseId){

        List<CourseDetlVo> detlList = courseDetlRepository.findByCourseId(courseId);

        StringBuilder sb = new StringBuilder();

        if(detlList.size() > 0) {
            sb.append("&markers=type:d|size:mid|color:red");
        }

        for(CourseDetlVo vo : detlList){
            System.out.println("vo.getCourseId() : "+vo.getCourseId());
            sb.append("|pos:")
                    .append(vo.getCourseLongitude())
                    .append("%20")
                    .append(vo.getCourseLatitude());

        }

        return sb.toString();
    }



    public int createTmpCreateHist(Integer courseId){

        CourseHistVo vo = courseHistRepository.save(CourseHistVo.builder()
                                                                        .courseId(courseId)
                                                                        .build());

        System.out.println("vo.getId() : "+vo.getId());

        return 0;
    }



}
