package com.play.walk.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

//20220104 DB에 실제로 테이블 생성할 것

@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_COURSE_HIST_GEN", sequenceName="SEQ_COURSE_HIST", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="course_hist")  //JPA의 Entity
public class CourseHistVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_COURSE_HIST_GEN")
    private Integer id;
    private String courseId;
    private Timestamp insDtm;


    @Builder
    public CourseHistVo(Integer id, String courseId, String courseName, String useYn, Timestamp insDtm) {
        this.id = id;
        this.courseId = courseId;
        this.insDtm = insDtm;
    }
}
