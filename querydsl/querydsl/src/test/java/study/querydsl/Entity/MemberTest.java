package study.querydsl.Entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberTest {


    @Autowired
    EntityManager em;


    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA); // 영속성 컨텍스트에 저장
        em.persist(teamB);
        // 영속성 컨텍스트란 엔티티를 영구 저장하는 환경이다.
        // JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어야 한다는 것이다.
        // 영속성 컨텍스트에 포함된 엔티티는 엔티티의 생명 주기를 관리한다.

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();

        // 확인
        em.createQuery("select m from Member m", Member.class)
                .getResultList()
                .forEach(m -> {
                    System.out.println("member = " + m);
                    System.out.println("-> member.team = " + m.getTeam());
                });
    }
}