package org.hackathon.economy.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "MEMBER")
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "member_name", nullable = false)
    private String memberName;
    @Column(name = "member_grade", nullable = false)
    private Integer memberGrade;
    @Column(name = "member_point", nullable = false)
    private Long memberPoint;
    @Column(name = "member_email", nullable = false)
    private String memberEmail;
    @Column(name = "member_password", nullable = false)
    private String memberPassword;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "update_date", nullable = false)
    private Date updatedDate;

}
