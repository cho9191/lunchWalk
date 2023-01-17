package com.play.walk.mapper;

import com.play.walk.vo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("select h.course_id as courseId" +
            ", h.course_name as courseName" +
            ", h.course_kcal as courseKcal" +
            ", h.course_length as courseLength" +
            ", h.course_img_url as courseImgUrl" +
            ", (select count(hst.course_id)" +
            " from course_hist hst " +
            " where hst.course_id = h.course_id) as courseExecuteCnt" +
            " ,5 as courseSatisfyPoint" +
            ", (select u.user_name from user_info u where u.user_id = h.course_create_user_id) as courseCreateUserName" +
            ", to_char(h.ins_dtm, 'YYYY-MM-DD') as courseInsDtm" +
            " from course_h h" +
            " where 1=1 " +
            " and h.course_id = #{courseId} ")
    List<CourseRtnVo> searchCourse(String courseId);


    @Select("select t3.course_name as courseName " +
                         ", t3.course_id as courseId" +
                         ", t3.course_kcal as courseKcal " +
                         ", t3.course_length as courseLength " +
                         ", t3.ins_dtm as insDtm " +
                         ", t3.course_create_user_name as courseCreateUserName " +
                "from(" +
                    "select t1.course_name" +
                            ", t1.course_id " +
                            ", t1.course_kcal " +
                            ", t1.course_length " +
                            ", (ROW_NUMBER() OVER(order by t2.ins_dtm desc)) as rownum" +
                            ", (select u.user_name  from user_info u where u.user_id = t1.course_create_user_id) as course_create_user_name " +
                            ", t2.ins_dtm " +
                    "from course_h t1" +
                            ", course_hist t2" +
                            " where 1=1" +
                            " and t1.course_id = t2.course_id" +
                            " and to_char(t2.ins_dtm,'YYYYMMDD') = to_char(now(), 'YYYYMMDD')  " +
                            " order by t2.ins_dtm desc" +
                            " ) t3" +
                            " where rownum  =1")
    CourseRtnVo todayCourse();


    @Select("select \n" +
            "  t1.id as courseHistId\n" +
            ", t2.course_id as courseId \n" +
            ", t2.course_name as courseName\n" +
            ", t2.course_kcal as courseKcal\n" +
            ", t2.course_length as courseLength\n" +
            ", to_char(t1.ins_dtm , 'YYYY-MM-DD')  as courseDtm\n" +
            ", (select coalesce(ARRAY_TO_STRING(ARRAY_AGG(u.user_name),', '),'')\n" +
                        " from user_info u\n" +
                        " where 1=1\n" +
                        " and u.user_id in\n" +
                        " (select t3.user_id \n" +
                        " from course_hist_attendee t3\n" +
                        " where t3.course_hist_id = t1.id)\n" +
                        ") as courseAttendee\n" +
            "from course_hist t1\n" +
            "    ,course_h t2\n" +
            "where 1=1\n" +
            "and t1.course_id = t2.course_id\n" +
            "order by t1.ins_dtm desc")
    List<CourseHistRtnVo> walkHist();

    @Delete("delete from course_hist \n" +
            "where 1=1\n" +
            "and to_char(ins_dtm , 'YYYY-MM-DD')  = to_char(now() , 'YYYY-MM-DD')")
    int deleteCourse();



    @Select("select  t1.course_hist_id \n" +
            ", to_char(t1.ins_dtm , 'YYYY-MM-DD')  as courseDtm\n" +
            ", t2.course_id\n" +
            ", (select t3.course_name from course_h t3 where t3.course_id = t2.course_id) as courseName\n" +
            "from course_hist_attendee t1\n" +
            "left outer join course_hist t2\n" +
            "   on t1.course_hist_id = t2.id \n" +
            "where 1=1\n" +
            "and t1.user_id = #{userId}\n" +
            "order by t1.ins_dtm desc")
    List<CourseMyWalkHistRtnVo> myWalkHist(String userId);

    @Select("select t5.user_id as userId\n" +
            ",sum(t5.course_length) as courseLengthSum\n" +
            ",sum(t5.course_kcal) as courseKcalSum\n" +
            "from(\n" +
            "select t1.user_id\n" +
            ",t2.course_id \n" +
            ", (select t3.course_length from course_h t3 where t3.course_id = t2.course_id ) as course_length\n" +
            ", (select t3.course_kcal  from course_h t3 where t3.course_id = t2.course_id ) as course_kcal\n" +
            "from course_hist_attendee t1\n" +
            " left outer join course_hist t2\n" +
            " on t1.course_hist_id = t2.id \n" +
            "where t1.user_id = #{userId}\n" +
            ") as t5\n" +
            "group by t5.user_id")
    CourseMyWalkHistHeaderRtnVo myWalkHistSumInfo(String userId);

}