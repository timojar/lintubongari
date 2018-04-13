package com.example.birdchannel.model;

import java.util.Map;

public class JsonObject {


    private Map birds;

    public JsonObject(Map birds) {
        this.birds = birds;
    }

    public void setBirds(Map birds) {
        this.birds = birds;
    }


    public JsonObject() {

    }

    ;


    @Override
    public String toString() {
        return "JsonObject{" +
                "birds=" + birds +
                '}';
    }
}
