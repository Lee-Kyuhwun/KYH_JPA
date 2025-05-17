package dev.be.ex1hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaBasicApplication {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// hello라는 이름의 persistence unit을 찾아서 EntityManagerFactory를 만들어줌
        EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
        try{

            Team team = new Team();
            team.setName("Team 1");
            em.persist(team);

            Member member = new Member();
            member.setName("Member 1");
            member.changeTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());

            Team findTeam = findMember.getTeam();
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("Member name: " + m.getName());
            }




        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }

    }

}
