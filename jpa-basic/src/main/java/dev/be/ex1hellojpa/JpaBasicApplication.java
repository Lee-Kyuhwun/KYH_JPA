package dev.be.ex1hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaBasicApplication {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// hello라는 이름의 persistence unit을 찾아서 EntityManagerFactory를 만들어줌
        EntityManager entityManager = emf.createEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
        try{


            Member member = new Member();
            member.setRoleType(RoleType.USER);

            Member findMember = entityManager.find(Member.class, 1L);
            Class<Member> memberClass = Member.class;
            entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList()
                    .forEach(System.out::println);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            entityManager.close();
        }
        // JPQL 엔티티 객체를 대상으로 검색한다.
        // SQL은 데이터 베이스 테이블을 대상으로 쿼리
        // JPQL은 엔티티 객체를 대상으로 쿼리
        // JPQL은 SQL을 추상화한 객체 지향 쿼리 언어
		Member member = new Member();

		entityManager.persist(member);
		tx.commit();

        entityManager.close();
        emf.close();

    }

}
