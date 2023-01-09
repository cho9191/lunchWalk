package com.play.walk.vo;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

//20220104 DB에 실제로 테이블 생성할 것

@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_COURSE_H_GEN", sequenceName="SEQ_COURSE_H", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="course_h")  //JPA의 Entity
public class CourseHVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_COURSE_H_GEN")
    private Integer id;
    private String courseId;
    private String courseName;
    private String courseCreateUserId;
    private String useYn;
    private Timestamp insDtm;

    @Builder
    public CourseHVo(Integer id, String courseId, String courseName, String courseCreateUserId, String useYn, Timestamp insDtm) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCreateUserId = courseCreateUserId;
        this.useYn = useYn;
        this.insDtm = insDtm;
    }
}
