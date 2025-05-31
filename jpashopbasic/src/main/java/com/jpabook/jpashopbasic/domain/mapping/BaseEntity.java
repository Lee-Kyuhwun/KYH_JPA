package com.jpabook.jpashopbasic.domain.mapping;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
// MappedSuperclass 어노테이션을 사용하면 이 클래스를 상속받는 엔티티 클래스들은
// 이 클래스의 필드들을 자신의 필드로 가지게 됩니다.
public abstract class BaseEntity {

    private Long createdBy;
    private Long lastModifiedBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
