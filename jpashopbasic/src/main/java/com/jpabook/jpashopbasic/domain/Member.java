package com.jpabook.jpashopbasic.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;




    private String name;

    private String city;

    private String street;

    private String zipcode;

    @OneToMany(mappedBy = "Member")
    private List<Order> orders = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // 실무에서는 이렇게 사용 X
//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT")
//    private List<Product> products = new ArrayList<>();


    @OneToMany
    private List<MemberProduct> memberProducts = new ArrayList<>();




}
