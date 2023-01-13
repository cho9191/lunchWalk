package com.play.walk.repository;

import com.play.walk.vo.CourseDetlVo;
import com.play.walk.vo.CourseHVo;
import com.play.walk.vo.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDetlRepository extends JpaRepository<CourseDetlVo, Integer> {

    List<CourseDetlVo> findByCourseId(int courseId);


}
