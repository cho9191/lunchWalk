package com.play.walk.mapper;

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
}
