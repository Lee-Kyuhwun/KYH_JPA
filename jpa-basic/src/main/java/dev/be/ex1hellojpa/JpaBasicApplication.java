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
            Member findMember = entityManager.find(Member.class, 1L);
            Class<Member> memberClass = Member.class;
            entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList()
                    .forEach(System.out::println);
            findMember.setName("HelloJPA");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            entityManager.close();
        }

		Member member = new Member();
		member.setId(1L);
		member.setName("HelloA");
		entityManager.persist(member);
		tx.commit();

        entityManager.close();
        emf.close();

    }

}
