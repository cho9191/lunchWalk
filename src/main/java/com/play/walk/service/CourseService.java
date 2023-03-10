package com.play.walk.service;

import com.play.walk.mapper.CourseMapper;
import com.play.walk.repository.CourseDetlRepository;
import com.play.walk.repository.CourseHRepository;
import com.play.walk.repository.CourseHistAttendeeRepository;
import com.play.walk.repository.CourseHistRepository;
import com.play.walk.utils.CalcTimeZone;
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
    private CourseHistAttendeeRepository courseHistAttendeeRepository;
    @Autowired
    private CourseMapper courseMapper;

    public static String URL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster-cors?w=500&h=500";
    public static String CENTER = "&center=127.1033938,37.4027288";
    public static String LEVEL = "&level=14";  //0~20 사이 클 수록 확대
    public static String KEY = "&X-NCP-APIGW-API-KEY-ID=wq1hwomit5";
    public static String MARKER_SIZE = "small";  //tiny, samll, mid
    public static String MARKER_COLOR = "Orange";  //Orange, Red, Green, Blue

    public int createCourse(String courseName, String courseLatitude, String courseLongitude
            , double courseLength, double courseKcal){

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


    public CourseRtnVo createCourse(String isAutoYn, String courseId){

        //이미 확정된 코스가 있는지 재확인
        CourseRtnVo todayVo = courseMapper.todayCourse();
        if(todayVo != null){
            return null;
        }

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

            List<CourseRtnVo> courseRtnVoList = courseMapper.searchCourseWithCourseID(Integer.parseInt(courseId));

            CourseHistVo vo = courseHistRepository.save(CourseHistVo.builder()
                    .courseId(Integer.parseInt(courseId))
                    .autoYn("N")
                    .build());

            return courseRtnVoList.get(0);
        }
    }

    public int cancelCourse(){
        courseMapper.deleteCourseAttendeeHist();
        return courseMapper.deleteCourseHist();
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
        log.info("randomVal : "+randomVal);

        return randomVal;
    }


    public String mapUrl(int courseId){

        String url = URL;
        String center = CENTER;
        String level = LEVEL;
        String key = KEY;

        String marker = this.getMarker(courseId);

        StringBuilder sb = new StringBuilder();

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
            sb.append("&markers=type:d|size:"+MARKER_SIZE+"|color:"+MARKER_COLOR);
        }

        CalcTimeZone calcTimeZone = new CalcTimeZone("Asia/Seoul");

        System.out.println("타임존 체크");
        System.out.println(calcTimeZone.getNowSeoultime());
        System.out.println("test123 : "+calcTimeZone.getNowSeoultime());

        for(CourseDetlVo vo : detlList){
            System.out.println("vo.getCourseId() : "+vo.getCourseId());
            sb.append("|pos:")
                    .append(vo.getCourseLongitude())
                    .append("%20")
                    .append(vo.getCourseLatitude());
        }
        return sb.toString();
    }


    public int courseHistAttend(String courseHistIds){

        String[] arrCourseHistIds = courseHistIds.split(",");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = (String) principal;
        int saveCnt = 0;

        for(String str : arrCourseHistIds){

            int courseHistId = Integer.parseInt(str);

            int exsistHistCnt = courseHistAttendeeRepository.countByUserIdAndCourseHistId(userId, courseHistId);
            System.out.println("exsistHistCnt : "+exsistHistCnt);
            if(exsistHistCnt > 0){
                continue;
            }

            courseHistAttendeeRepository.save(CourseHistAttendeeVo.builder()
                    .courseHistId(courseHistId)
                    .userId(userId)
                    .build()
            );
            saveCnt++;
        }

        return saveCnt;
    }

    public CourseMyWalkHistHeaderRtnVo courseMyWalkHist(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = (String) principal;

        CourseMyWalkHistHeaderRtnVo sumVo = courseMapper.myWalkHistSumInfo(userId);
        sumVo.setCourseMyWalkHistRtnVoList(courseMapper.myWalkHist(userId));

        return sumVo;
    }



    public int createTmpCreateHist(Integer courseId){

        CourseHistVo vo = courseHistRepository.save(CourseHistVo.builder()
                                                                        .courseId(courseId)
                                                                        .build());

        return 0;
    }


    public String previewMapUrl(String courseLatitude, String courseLongitude){

        String url = URL;
        String center = CENTER;
        String level = LEVEL;
        String key = KEY;

        String marker = this.getPreviewMarker(courseLatitude, courseLongitude);

        StringBuilder sb = new StringBuilder();

        return sb.append(url)
                .append(center)
                .append(level)
                .append(marker)
                .append(key)
                .toString();
    }

    public String getPreviewMarker(String courseLatitude, String courseLongitude){

        StringBuilder sb = new StringBuilder();

        String[] arrCourseLatitudes = courseLatitude.split(",");
        String[] arrCourseLongitudes = courseLongitude.split(",");

        if(arrCourseLatitudes.length > 0) {
            sb.append("&markers=type:d|size:"+MARKER_SIZE+"|color:"+MARKER_COLOR);
        }

        for(int i=0; i<arrCourseLatitudes.length; i++){
            sb.append("|pos:")
                    .append(arrCourseLongitudes[i])
                    .append("%20")
                    .append(arrCourseLatitudes[i]);
        }
        return sb.toString();
    }


}
