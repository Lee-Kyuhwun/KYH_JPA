package study.querydsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import study.querydsl.Entity.Member;
import study.querydsl.Entity.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.Entity.QTeam;
import study.querydsl.Entity.Team;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.*;
import static study.querydsl.Entity.QMember.member;

import java.util.List;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {


    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA); // 영속성 컨텍스트에 저장
        em.persist(teamB); // 영속성 컨텍스트에 저장
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    public void startJPQL() {
        //member1을 찾아라
        String qlString = "select m from Member m where m.username = :username";

        Member findMember = em.createQuery(qlString, Member.class) // qlString을 Member.class로 변환
                .setParameter("username", "member1") // 파라미터 바인딩
                .getSingleResult(); // 결과가 없으면 javax.persistence.NoResultException 예외 발생

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // JPAQueryFactory를 사용하려면 EntityManager를 주입받아야 함
        QMember m = new QMember("m"); // QMember를 사용하기 위해 QMember m = new QMember("m")으로 생성

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne(); // 결과가 없으면 null 반환

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void startQuerydsl2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em); // JPAQueryFactory를 사용하려면 EntityManager를 주입받아야 함
        QMember m = new QMember("m"); // QMember를 사용하기 위해 QMember m = new QMember("m")으로 생성

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne(); // 결과가 없으면 null 반환

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl3() {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne(); // 결과가 없으면 null 반환

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne(); // 결과가 없으면 null 반환

        assertThat(findMember.getUsername()).isEqualTo("member1");

    }

    @Test
    public void searchAndParam() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"),
                        member.age.eq(10))
                .fetchOne(); // 결과가 없으면 null 반환

        assertThat(findMember.getUsername()).isEqualTo("member1");

    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();

    }


    @Test
    public void paging1() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1) // 0부터 시작(zero index)
                .limit(2) // 최대 2건 조회
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }

    /**
     * JPQL
     * select
     * COUNT(m), //회원수
     * SUM(m.age), //나이 합
     * AVG(m.age), //평균 나이
     * MAX(m.age), //최대 나이
     * MIN(m.age) //최소 나이
     * from Member m
     */
    @Test
    public void aggregation() throws Exception {
        List<Tuple> result = queryFactory
                .select(member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min())
                .from(member)
                .fetch();
        Tuple tuple = result.get(0);
        Assertions.assertThat(tuple.get(member.count())).isEqualTo(4);
        Assertions.assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        Assertions.assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        Assertions.assertThat(tuple.get(member.age.max())).isEqualTo(40);
        Assertions.assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    public void group() throws Exception {
        List<Tuple> result = queryFactory
                .select(QTeam.team.name, member.age.avg())
                .from(member)
                .join(member.team, QTeam.team)
                .groupBy(QTeam.team.name)
                .fetch();
        System.out.println("result = " + result);
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        Assertions.assertThat(teamA.get(QTeam.team.name)).isEqualTo("teamA");
        Assertions.assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        Assertions.assertThat(teamB.get(QTeam.team.name)).isEqualTo("teamB");
        Assertions.assertThat(teamB.get(member.age.avg())).isEqualTo(35);

    }


    /**
     * 팀 A에 소속된 모든 회원
     */

    @Test
    public void join() throws Exception {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, QTeam.team)
                .where(QTeam.team.name.eq("teamA"))
                .fetch();


        assertThat(result).extracting("username").containsExactly("member1", "member2");

    }

    /**
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    public void theata_join() throws Exception {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> result = queryFactory
                .select(member)
                .from(member, QTeam.team)
                .where(member.username.eq(QTeam.team.name))
                .fetch();

        assertThat(result).extracting("username").containsExactly("teamA", "teamB");
    }


    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'teamA'
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and
     * t.name='teamA'
     */
    @Test
    public void join_on_filtering() throws Exception {
        List<Tuple> result = queryFactory
                .select(member, QTeam.team)
                .from(member)
                .leftJoin(member.team, QTeam.team).on(QTeam.team.name.eq("teamA"))// 연관관계가 없어도 조인 가능
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() throws Exception {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf
                .getPersistenceUnitUtil() // getPersistenceUnitUtil()을 사용하면 영속성 유닛 유틸리티를 통해 영속성 컨텍스트에 로딩 여부 확인 가능
                .isLoaded(findMember.getTeam());  // isLoaded()를 사용하면 페치 조인 미적용을 확인 가능
        assertThat(loaded).as("페치 조인 미적용").isFalse(); // 페치 조인 미적용

    }

    // 즉시로딩
    @Test
    // 페치 조인이란 연관된 엔티티를 SQL 한 번에 함께 조회하는 기능
    // 페치 조인을 사용하면 연관된 엔티티를 SQL 한 번에 함께 조회할 수 있음
    // 페치 조인은 연관된 엔티티를 SQL 한 번에 조회하는 기능
    // 페치 조인은 연관된 엔티티를 SQL 한 번에 함께 조회하는 기능
    public void fetchJoinUse() throws Exception {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, QTeam.team).fetchJoin() // 페치 조인 적용
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf
                .getPersistenceUnitUtil()
                .isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 적용").isTrue(); // 페치 조인 적용

    }

    // 서브 쿼리 이용
    @Test
    public void subQuery() throws Exception {
        QMember memberSub = new QMember("memberSub"); // 서브쿼리의 별칭은 필수

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        // JPAExpressions를 사용하면 서브쿼리를 사용할 수 있음
                        select(memberSub.age.max()) // 서브쿼리의 최대 나이를 조회
                                .from(memberSub) // 서브쿼리의 별칭을 지정
                )).fetch();

        assertThat(result).extracting("age").containsExactly(40);
    }


    // 서브 쿼리 goe 사용
    @Test
    public void subQueryGoe() throws Exception {
        QMember memberSub = new QMember("memberSub"); // 서브쿼리의 별칭은 필수

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe( // goe는 크거나 같다
                        // JPAExpressions를 사용하면 서브쿼리를 사용할 수 있음
                        select(memberSub.age.avg()) // 서브쿼리의 평균 나이를 조회
                                .from(memberSub) // 서브쿼리의 별칭을 지정
                )).fetch();

        assertThat(result).extracting("age").containsExactly(30, 40);
    }


    // 서브 쿼리 in 사용, 여러건 처리
    @Test
    public void subQueryIn() throws Exception{
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();
        List<Tuple> fetch = queryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub)
                ).from(member)
                .fetch();


        for (Tuple tuple : fetch) {
            System.out.println("username = " + tuple.get(member.username));
            System.out.println("age = " +
                    tuple.get(select(memberSub.age.avg())
                            .from(memberSub)));
        }
        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }


    // 동적쿼리 BooleanBuilder 사용
    @Test
    public void 동적쿠러_BooleanBuilder_사용() throws Exception{
            String usernameParam = "member1";

            Integer ageParam = 10;

            List<Member> result = searchMember1(usernameParam, ageParam);

            assertThat(result.size()).isEqualTo(1);

    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond){
        BooleanBuilder builder = new BooleanBuilder();

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond)); // and 조건 추가
        }

        if(ageCond != null){
            builder.and(member.age.eq(ageCond)); // and 조건 추가
        }

        return queryFactory.selectFrom(member).where(builder).fetch(); // where에 builder를 사용

    }


    // 동적쿼리 Where 다중 파라미터 사용

    @Test
    public void 동적쿼리_Where_다중파라미터_사용() throws Exception{
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond){
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond){
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }
    private BooleanExpression ageEq(Integer ageCond){
        return ageCond != null ? member.age.eq(ageCond) : null;
    }





}
