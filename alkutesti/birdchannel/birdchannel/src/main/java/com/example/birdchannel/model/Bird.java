package com.example.birdchannel.model;

/**
 * Created by a1500863 on 11.4.2018.
 */
public class Bird {


    private int id;
    private String name;

    public Bird(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Bird{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
