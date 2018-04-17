package com.example.birdchannel.model;



import java.sql.Timestamp;
import java.util.List;

/**
 * Created by a1500863 on 11.4.2018.
 */
public class Bird {


    private int id;
    private String name;
    private int quantity;
    private List<String> birdTime;

    public Bird() {
    }

    public Bird(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bird(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Bird(int id, String name, List<String> birdTime) {
        this.id = id;
        this.name = name;
        this.birdTime = birdTime;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getBirdTime() {
        return birdTime;
    }

    public void setBirdTime(List<String> birdTime) {
        this.birdTime = birdTime;
    }

    @Override
    public String toString() {
        return "Bird{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", birdTime=" + birdTime +
                '}';
    }
}
