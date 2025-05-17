package com.jpabook.jpashopbasic.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order; // 주문

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;



    private int orderPrice; // 주문 가격
    private int count;
}
