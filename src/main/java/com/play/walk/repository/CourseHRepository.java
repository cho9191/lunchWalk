package com.play.walk.repository;

import com.play.walk.vo.CourseHVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseHRepository extends JpaRepository<CourseHVo, Integer> {


}
