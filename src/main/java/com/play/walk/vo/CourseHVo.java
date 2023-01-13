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
    private Integer courseId;
    private String courseName;
    @Setter
    private String courseCreateUserId;
    private double courseLength;
    private double courseKcal;
    private String courseImgUrl;
    private String courseRole; //팀이 여러 팀일 경우 대비
    private String useYn;
    private Timestamp insDtm;

    @PrePersist
    public void prePersist() {
        this.useYn = this.useYn == null ? "Y" : this.useYn;
        this.insDtm = this.insDtm ==null ? new java.sql.Timestamp(new Date().getTime()) : this.insDtm;
    }
    @Builder
    public CourseHVo(Integer courseId, String courseName, String courseCreateUserId, double courseLength, double courseKcal, String courseImgUrl, String courseRole, String useYn, Timestamp insDtm) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCreateUserId = courseCreateUserId;
        this.courseLength = courseLength;
        this.courseKcal = courseKcal;
        this.courseImgUrl = courseImgUrl;
        this.courseRole = courseRole;
        this.useYn = useYn;
        this.insDtm = insDtm;
    }
}
