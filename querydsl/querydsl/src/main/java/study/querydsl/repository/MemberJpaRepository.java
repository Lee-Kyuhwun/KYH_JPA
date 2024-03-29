package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Repository;
import study.querydsl.Entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {



    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public MemberJpaRepository(EntityManager em, JPAQueryFactory queryFactory) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);

        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username",Member.class)
                .setParameter("username", username)
                .getResultList();
    }



    public void deleteAll() {
        em.createQuery("delete from Member").executeUpdate();
    }


}
