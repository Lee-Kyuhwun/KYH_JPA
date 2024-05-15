package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember();
        Book book = createBook(10000, "시골 JPA", 10);
        // When
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        // Then
        Order order = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER",  OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000*2, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, book.getStockQuantity());
    }

    private Book createBook(int orderPrice, String name, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUserName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));

        em.persist(member);
        return member;
    }


    @Test
    public void 주문취소() throws Exception {
        // Given
        Member member = createMember();
        Book item = createBook(10000, "시골 JPA", 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);
        // Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // Given
        Member member = createMember();
        Book item = createBook(10000, "시골 JPA", 10);
        int orderCount = 11;

        // When
        // Then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

    }



}