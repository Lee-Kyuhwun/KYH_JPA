package com.jpabook.jpashopbasic.domain.mapping;

public class Movie extends Item{

    private String director;
    private String actor;

    // Getters and Setters
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
