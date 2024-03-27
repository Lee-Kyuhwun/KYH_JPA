package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // 엔티티 매니저를 주입받을 수 있음
    private EntityManager em;

    public void save(Member member){
        em.persist(member); // 영속성 컨텍스트에 저장
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }


    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.userName = :userName",Member.class)
                .setParameter("userName",name)
                .getResultList();
    }
}
