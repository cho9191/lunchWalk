package com.play.walk.repository;

import com.play.walk.vo.CourseDetlVo;
import com.play.walk.vo.CourseHistAttendeeVo;
import com.play.walk.vo.CourseHistVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseHistAttendeeRepository extends JpaRepository<CourseHistAttendeeVo, Integer> {

    int countByUserIdAndCourseHistId(String userId, int courseHistId);


}
