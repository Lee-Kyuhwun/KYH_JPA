package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // JPA의 EntityManager를 주입받을 수 있음
    private EntityManager em;

    public Long save(Member member){
        em.persist(member); // 영속성 컨텍스트에 member 객체를 넣는다.
        // 영속성 컨텍스트란 엔티티를 영구 저장하는 환경이다.
        // JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어야 한다는 것이다.
        // 영속성 컨텍스트에 포함된 엔티티는 엔티티의 생명 주기를 관리한다.
        return member.getId(); // member.getId()는 DB에 저장된 후에 자동으로 할당된 값이다.
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
