package dev.be.ex1hellojpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    private Long teamId;

    private String name;

    @OneToMany(mappedBy = "team") // mappedBy: team이 연관관계의 주인이라는 것을 나타냄
    private List<Member> members = new ArrayList<>();
    // 외래키가 있는 곳을 주인으로 정하라

    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }



}
