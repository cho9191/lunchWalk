package com.play.walk.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//20220104 DB에 실제로 테이블 생성할 것

@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_COURSE_DETL_GEN", sequenceName="SEQ_COURSE_DETL", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="course_detl")  //JPA의 Entity
public class CourseDetlVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_COURSE_DETL_GEN")
    private Integer id;
    private String courseId;
    private Integer seq;
    private String courseLatitude;
    private String courseLongitude;
    private String useYn;
    private Timestamp insDtm;

    @Builder
    public CourseDetlVo(Integer id, String courseId, Integer seq, String courseLatitude, String courseLongitude, String useYn, Timestamp insDtm) {
        this.id = id;
        this.courseId = courseId;
        this.seq = seq;
        this.courseLatitude = courseLatitude;
        this.courseLongitude = courseLongitude;
        this.useYn = useYn;
        this.insDtm = insDtm;
    }
}
