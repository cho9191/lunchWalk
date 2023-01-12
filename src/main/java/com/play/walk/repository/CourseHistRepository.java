package com.play.walk.repository;

import com.play.walk.vo.CourseHVo;
import com.play.walk.vo.CourseHistVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseHistRepository extends JpaRepository<CourseHistVo, Integer> {


}
