package dev.be.ex1hellojpa;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Mbr")
@SequenceGenerator(name = "member_seq")
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //유니크키는 table에서 유니크키로 설정해줘야함 그리고 보통 @table 애너테이션에 유니크 키를 설정한다
    // column에너테이션에서 유니크설정을 해주면 유니크키 이름이 알아보기 힘들어짐
    @Column(name = "name", nullable = false , insertable = true, updatable = true)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;
    // 외래 키가 있는 곳을 주인으로 정하라
    // 여기서는 Member가 team_id를 가지고 있으므로 Member가 주인이다
    // 1:N 관계에서 N쪽이 주인이다



    //
//    private BigDecimal age;
//
//    @Enumerated(EnumType.STRING) // EnumType.ORDINAL은 숫자로 저장됨
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간을 모두 저장
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    @Lob // Lob은 대용량 데이터를 저장할 때 사용
//    private String description;
//
//    @Transient // DB에 저장하지 않음
//    private int temp;
    public Member() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
