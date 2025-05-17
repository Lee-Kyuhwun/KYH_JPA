package com.jpabook.jpashopbasic;

import com.jpabook.jpashopbasic.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopbasicApplication {

    private CommandLineRunner 애플리케이션_시작됨;

    public static void main(String[] args) {
        SpringApplication.run(JpashopbasicApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return 애플리케이션_시작됨;
    }

    @Bean
    public CommandLineRunner demo(JPARunner jpaRunner) {
        return args -> {
            jpaRunner.run();
        };
    }
}

@org.springframework.stereotype.Component
class JPARunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void run() {
        try {
            Member member = new Member();
            // 필요시 member 속성 설정
            entityManager.persist(member);

            Member findMember = entityManager.find(Member.class, 1L);
            entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList()
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}