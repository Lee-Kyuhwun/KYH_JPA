package study.querydsl.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // AccessLevel.PROTECTED로 기본 생성자 생성
// Access.protected는 해당 클래스와 동일 패키지에 있는 클래스와 하위 클래스에서만 접근 가능
@ToString(of = {"id", "username", "age"}) // id, name, age만 출력
public class Member {

    @Id
    @GeneratedValue // 기본키 자동 생성
    @Column(name = "member_id") // 컬럼명 지정
    private Long id;


    private String username;

    private int age;


    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 매핑
    @JoinColumn(name = "team_id") // 외래키 매핑
    private Team team;


    public Member(String username) {
        this(username, 0);
    }
    public Member(String username, int age) {
        this(username, age, null);
    }
    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }




}
