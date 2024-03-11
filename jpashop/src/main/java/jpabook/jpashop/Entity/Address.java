package jpabook.jpashop.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embeddable // 값 타입을 정의하는 곳에 표시한다.
// Embeddable은 값 타입을 정의할 때 사용하는 어노테이션이다.
// 값 타입은 불변으로 만들어야 한다.
// 값 타입은 임베디드 타입(복합 값 타입)이나 기본 값 타입을 포함할 수 있다.
// 임베디드 타입은 엔티티의 값으로 포함되어서 엔티티의 생명 주기에 의존한다.
// 임베디드 타입은 엔티티의 생명 주기에 의존하므로 값 타입을 포함한 엔티티는 공유하면 안 된다.
// 임베디드 타입을 포함한 엔티티를 공유하려면 복사해서 사용해야 한다.
// 임베디드 타입은 값 타입을 포함한 엔티티의 생명 주기에 의존하므로 값 타입을 포함한 엔티티를 공유하면 안 된다.
// 값 타입을 포함한 엔티티를 공유하려면 복사해서 사용해야 한다.
// 임베디드 타입은 엔티티의 생명 주기에 의존하므로 값 타입을 포함한 엔티티를 공유하면 안 된다.
// 값 타입을 포함한 엔티티를 공유하려면 복사해서 사용해야 한다.
// 임베디드 타입은 값 타입을 포함한 엔티티의 생명 주기에 의존하므로 값 타입을 포함한 엔티티를 공유하면 안 된다.
public class Address {

    private String city;

    private String street;

    private String zipcode;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}