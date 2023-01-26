package com.play.walk.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

//20220104 DB에 실제로 테이블 생성할 것

@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@SequenceGenerator(name="SEQ_USER_INFO_GEN", sequenceName="SEQ_USER_INFO", initialValue = 1, allocationSize = 1)
@Getter
@Entity(name="user_info")  //JPA의 Entity
public class UserVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ_USER_INFO_GEN")
    private Integer id;
    private String userId;
    private String userName;
    @Setter
    private String userPassword;
    //@ColumnDefault("USER")
    private String userRole;
    private String useYn;
    private Timestamp insDtm;

    @PrePersist
    public void prePersist() {
        //ColumnDefault 어노테이션이 안먹음
        //아래와 같이 기본 값 설정
        this.userRole = this.userRole == null ? "USER" : this.userRole;
        this.useYn = this.useYn == null ? "Y" : this.useYn;
        this.insDtm = this.insDtm ==null ? new java.sql.Timestamp(new Date().getTime()) : this.insDtm;
    }

    @Builder
    public UserVo(Integer id, String userId, String userName, String userPassword, String userRole, String useYn, Timestamp insDtm) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.useYn = useYn;
        this.insDtm = insDtm;
    }
}
