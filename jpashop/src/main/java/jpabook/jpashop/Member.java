package jpabook.jpashop;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Getter@Setter
public class Member {


    @Id
    @GeneratedValue
    private Long id;

    private String username;
}
