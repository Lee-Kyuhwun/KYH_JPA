package dev.be.ex1hellojpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    private Long teamId;

    private String name;

    @OneToMany
    private List<Member> members;
}
