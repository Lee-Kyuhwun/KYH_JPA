package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {


    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    private String author;
    private String isbn; // ISBN은 국제 표준 도서 번호로 어떤 책에도 중복되지 않는 고유한 번호이다.
}
