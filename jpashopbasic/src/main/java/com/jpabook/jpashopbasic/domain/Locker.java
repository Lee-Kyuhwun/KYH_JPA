package com.jpabook.jpashopbasic.domain;


import jakarta.persistence.*;

@Entity

public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Member member;

}
