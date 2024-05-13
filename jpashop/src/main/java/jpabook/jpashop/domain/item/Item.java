package jpabook.jpashop.domain.item;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
// @Inhertance는 상속관계 매핑을 위한 어노테이션으로, 부모 클래스에 사용한다.
// @Inheritance(strategy = InheritanceType)으로 전략을 설정할 수 있다.
// SINGLE_TABLE은 단일 테이블 전략으로, 모든 클래스를 한 테이블에 매핑한다.
@DiscriminatorColumn(name = "dtype") // 부모 클래스에 구분 컬럼을 지정한다.
@Getter
@Setter
public abstract class Item { // 추상 클래스이란 미완성 설계도이며, 객체를 직접 생성할 수 없다.
    // 여기는 구현체를 가지고 할것으로 추상클래스로 만들어준다.

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity; // 재고 수량

    @ManyToMany
    private List<Category> categoryies = new ArrayList<>();

    //==비즈니스로직==
    // 재고를 늘리고 줄이는 로직

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
