package jpabook.jpashop.domain;


import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"), // 중간 테이블을 사용하여 다대다 관계를 일대다, 다대일 관계로 풀어낸다.
            inverseJoinColumns = @JoinColumn(name = "item_id") // 중간 테이블을 사용하여 다대다 관계를 일대다, 다대일 관계로 풀어낸다.
    )
    private List<Item> items = new ArrayList<>();
    /**
     * 네, 가능합니다. `Item`이 추상 클래스라도 `Item` 타입의 참조 변수를 사용하여 `Item`의 하위 클래스의 인스턴스를 참조하는 것이 가능합니다. 이는 다형성의 한 예입니다. 이렇게 하면 `Item` 클래스를 상속받는 여러 다른 클래스의 인스턴스를 `Item` 타입의 리스트에 저장할 수 있습니다.
     * 하지만, `Item`이 추상 클래스이므로 `Item`의 인스턴스를 직접 생성할 수는 없습니다. 따라서 리스트에 추가할 수 있는 것은 `Item`의 하위 클래스의 인스턴스만 가능합니다.
     * 이 코드는 `Item`의 하위 클래스의 인스턴스를 `items` 리스트에 저장하려는 의도로 보입니다.
     * */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();




}
