package com.jpabook.jpashopbasic.domain.mapping;


import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// 단일 테이블 전략
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 테이블 전략
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;

    private int price;
}
