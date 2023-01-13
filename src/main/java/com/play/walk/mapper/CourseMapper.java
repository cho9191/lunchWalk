package com.play.walk.mapper;

import com.play.walk.vo.CourseHistRtnVo;
import com.play.walk.vo.CourseRtnVo;
import com.play.walk.vo.UserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
            " from course_h h")
    List<CourseRtnVo> searchCourse();


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
            ", to_char(t2.ins_dtm , 'YYYY-MM-DD')  as courseDtm\n" +
            ", '추가 예정' as courseAttendee\n" +
            "from course_hist t1\n" +
            "    ,course_h t2\n" +
            "where 1=1\n" +
            "and t1.course_id = t2.course_id\n" +
            "order by t1.ins_dtm desc")
    List<CourseHistRtnVo> walkHist();

}