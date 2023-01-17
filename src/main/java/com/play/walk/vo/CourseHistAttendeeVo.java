package com.play.walk.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_COURSE_HIST_ATTENDEE_GEN", sequenceName="SEQ_COURSE_HIST_ATTENDEE", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="course_hist_attendee")  //JPA의 Entity
public class CourseHistAttendeeVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_COURSE_HIST_ATTENDEE_GEN")
    private Integer id;
    private Integer courseHistId;
    private String userId;
    private Timestamp insDtm;


    @PrePersist
    public void prePersist() {
        this.insDtm = this.insDtm ==null ? new Timestamp(new Date().getTime()) : this.insDtm;
    }

    @Builder
    public CourseHistAttendeeVo(Integer id, Integer courseHistId, String userId, Timestamp insDtm) {
        this.id = id;
        this.courseHistId = courseHistId;
        this.userId = userId;
        this.insDtm = insDtm;
    }
}

