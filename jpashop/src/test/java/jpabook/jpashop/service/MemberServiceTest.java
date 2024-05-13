package jpabook.jpashop.service;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setUserName("kim");

        // when
        Long savedId = memberService.join(member);

        // then\
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setUserName("kim");
        Member member2 = new Member();
        member2.setUserName("kim");
        // when
        memberService.join(member1);
//        try{
//            memberService.join(member2); // 예외가 발생해야 한다.
//        }catch (IllegalStateException e){
//            return;
//        }
        assertThrows(IllegalStateException.class, ()-> memberService.join(member2)); // 예외가 발생해야 한다.
        // then
//        fail("예외가 발생해야 한다."); // 여기까지 오면 안된다. 예외가 터져서 나가야하기 때문
    }


}