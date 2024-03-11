package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // @DiscriminatorValue를 사용하여 구분값을 지정할 수 있다.
// @DiscriminatorValue를 사용하지 않으면 클래스명을 사용한다.
// 예를 들어, Book 클래스의 구분값은 "Book"이 된다.
// @DiscriminatorValue를 사용하면 "B"가 된다.
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
