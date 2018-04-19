package com.example.birdchannel.controller;

import com.example.birdchannel.model.Bird;


import com.google.gson.Gson;
import org.json.simple.JSONArray;

import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.FileReader;
import java.io.FileWriter;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by a1500863 on 11.4.2018.
 */


@Controller
public class Test {

    @Value("${tiedosto}")
    private String filepath;

    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @PostMapping(path = "/new")
    @ResponseBody
    public Bird addNote(@RequestBody String body) {
        Bird bird = parsingBird(body);
        System.out.println(bird.toString());
        List<Bird> birds = parseBirdFile();
        boolean succes = upDateFile(birds, bird);
        return new Bird(0, "", 0);
    }

    @PostMapping(path = "/newbird")
    @ResponseBody
    public String ceateBird(@RequestBody String body) {

        List<String> birdTime=new ArrayList();
        System.out.println(body.toString());
        List<Bird> birds = parseBirdFile();
        int id=birds.size()+1;
        Bird bird=new Bird(id,body.toString().replace("\"",""), birdTime);
        birds.add(bird);
        upDateFile(birds, bird);

        return "";
    }





    @GetMapping("/test")
    @ResponseBody
    public List<Bird> getBirds() {

        List<Bird> birds = parseBirdFile();

        if (birds.size() <= 0) {

            dummyBirds();
            birds = parseBirdFile();

        }
        System.out.println(birds.size() + " koko");


        return birds;
    }


    private List<Bird> dummyBirds() {


        List<String> birdTime = new ArrayList();
        System.out.println(birdTime);
        FileWriter file = null;
        String jsonInString = null;

        List<Bird> birds = new ArrayList<>();
        birds.add(new Bird(1, "harakka", birdTime));
        System.out.println(birds.get(0).toString());
        birds.add(new Bird(2, "varis", birdTime));
        System.out.println(birds.get(0).toString());
        System.out.println("tiedoston luonti");
        Gson gson = new Gson();
        try {
            file = new FileWriter(filepath);

            jsonInString = gson.toJson(birds);

            file.write(jsonInString);

            file.flush();
            file.close();

            ;
        } catch (Exception e) {

            e.printStackTrace();

        }
        return birds;


    }


    Bird parsingBird(String body) {


        org.springframework.boot.json.JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> result = springParser.parseMap(body);
        Bird bird = new Bird();
        String name = "";
        int id = 0;
        int quantity = 0;
        List<String> birdTime = new ArrayList();
        try {
            id = Integer.parseInt(result.get("id").toString());
            System.out.println(id);

            name = result.get("name").toString();
            bird.setId(id);
            String[] birdTimeJson = result.get("birdTime").toString().split(",");

            for (int e = 0; e < birdTimeJson.length; e++) {
                System.out.println(birdTimeJson[e] + " nro" + e);
                if (birdTimeJson[e].length() > 4) {
                    birdTime.add(birdTimeJson[e].replace("[", "").replace("]", "").trim());
                }
            }
            birdTime.add(new Timestamp(System.currentTimeMillis()).toString());


            bird.setName(name);
            bird.setBirdTime(birdTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bird;
    }


    private List<Bird> parseBirdFile() {


        JSONParser parser = new JSONParser();
        List<String> birdTime = new ArrayList();
        List<Bird> birds = new ArrayList();
        JSONArray jsonarray = null;
        org.springframework.boot.json.JsonParser springParser = JsonParserFactory.getJsonParser();
        int id = 0;
        String name;
        try {

            Object obj = parser.parse(new FileReader(
                    filepath));

            jsonarray = (JSONArray) obj;


            for (int i = 0; i < jsonarray.size(); i++) {
                Map<String, Object> result = springParser.parseMap(jsonarray.get(i).toString());

                System.out.println(jsonarray.toString() + " array nro" + i);
                id = Integer.parseInt(result.get("id").toString());
                name = result.get("name").toString();
                String[] birdTimeJson = result.get("birdTime").toString().split(",");
                System.out.println(birdTimeJson);
                for (int e = 0; e < birdTimeJson.length; e++) {
                    System.out.println(birdTimeJson[e] + " nro" + e);
                    if (birdTimeJson[e].length() > 4) {
                        birdTime.add(birdTimeJson[e].replace("[", "").replace("]", "").trim());
                    }

                }


                birds.add(new Bird(id, name, birdTime));
                birdTime = new ArrayList();

            }


        } catch (Exception e) {
            System.out.println("Tiedostoa ei löyytynyt");
        }
        return birds;
    }


    private boolean upDateFile(List<Bird> birds, Bird bird) {
        Gson gson = new Gson();
        boolean succes = false;
        FileWriter file = null;
        String jsonInString = null;

        for (int i = 0; i < birds.size(); i++) {


            if (birds.get(i).getId() == bird.getId()) {

                birds.set(i, bird);
                succes = true;


            }

        }


        try {
            file = new FileWriter(filepath);

            jsonInString = gson.toJson(birds);

            file.write(jsonInString);

            file.flush();
            file.close();

            ;
        } catch (Exception e) {

            e.printStackTrace();
            succes = false;

        }
        return succes;
    }




}




