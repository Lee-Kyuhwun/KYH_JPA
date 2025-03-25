package dev.be.ex1hellojpa;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Mbr")
public class Member {
    @Id
    private Long id;
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL은 숫자로 저장됨
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간을 모두 저장
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // Lob은 대용량 데이터를 저장할 때 사용
    private String description;

    @Transient // DB에 저장하지 않음
    private int temp;
    public Member() {
    }
}
