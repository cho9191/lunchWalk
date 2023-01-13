package com.play.walk.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

//20220104 DB에 실제로 테이블 생성할 것

@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_COURSE_HIST_GEN", sequenceName="SEQ_COURSE_HIST", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="course_hist")  //JPA의 Entity
public class CourseHistVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_COURSE_HIST_GEN")
    private Integer id;
    private Integer courseId;
    private String autoYn;
    private Timestamp insDtm;


    @PrePersist
    public void prePersist() {
        this.insDtm = this.insDtm ==null ? new java.sql.Timestamp(new Date().getTime()) : this.insDtm;
        this.autoYn = this.autoYn ==null ? "N" : this.autoYn;
    }

    @Builder
    public CourseHistVo(Integer id, Integer courseId, String autoYn, Timestamp insDtm) {
        this.id = id;
        this.courseId = courseId;
        this.autoYn = autoYn;
        this.insDtm = insDtm;
    }
}

