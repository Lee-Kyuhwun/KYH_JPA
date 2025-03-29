package dev.be.ex1hellojpa;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Mbr")
public class Member {
    @Id
    private Long id;
    //유니크키는 table에서 유니크키로 설정해줘야함 그리고 보통 @table 애너테이션에 유니크 키를 설정한다
    // column에너테이션에서 유니크설정을 해주면 유니크키 이름이 알아보기 힘들어짐
    @Column(name = "name", nullable = false , insertable = true, updatable = true)
    private String name;

    private BigDecimal age;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
