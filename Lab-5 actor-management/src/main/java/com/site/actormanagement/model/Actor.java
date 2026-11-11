package com.site.actormanagement.model;

public class Actor {
    private Integer id;
    private String name;
    private String movieName;

    public Actor() {
    }

    public Actor(Integer id, String name, String movieName) {
        this.id = id;
        this.name = name;
        this.movieName = movieName;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}