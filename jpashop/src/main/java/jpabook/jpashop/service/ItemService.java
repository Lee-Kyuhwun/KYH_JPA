package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// JPA의 모든 데이터 변경이나 로직은 트랜잭션 안에서 실행되어야 한다.
//readOnly는 성능이 좋아진다. 그리고 트랜잭션 안에서만 데이터를 변경할 수 있다.
// readONly란 데이터를 변경하지 않는 읽기 전용 메서드에 사용하면 성능이 최적화된다.

// readonly라는 옵션을 넣으면
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional// 이건 메서드에 넣었으니 readonly를 안먹는다.
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
