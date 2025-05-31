package com.jpabook.jpashopbasic.domain.mapping;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// 단일 테이블 전략
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 테이블 전략
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING) // 구분 컬럼
// 구분 컬럼은 상속 관계에서 어떤 자식 클래스인지 구분하기 위해 사용됩니다.
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;

    private int price;
}
