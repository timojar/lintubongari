package com.example.birdchannel.controller;

import com.example.birdchannel.model.Bird;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by a1500863 on 11.4.2018.
 */


@Controller
public class BirdController {

    @Value("${filelocation}")
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @PostMapping(path = "/new")
    @ResponseBody
    public String addNote(@RequestBody String body) {
        Bird bird = parsingBird(body);
        int quantity = bird.getSightings().size();
        String report = bird.getSightings().get(quantity - 1) + " uusi havainto: " + bird.getName() + "- kaikki havainnot: " + bird.getName() + " " + quantity + " kappaletta";

        List<Bird> birds = parseBirdFile();
        report = report + reportOtherBirds(birds, bird.getId());
        boolean succes = upDateFile(birds, bird);
        if (succes == true) {
            System.out.println(report);
            return "succes";
        }

        return "fail";
    }

    @PostMapping(path = "/newbird")
    @ResponseBody
    public String ceateBird(@RequestBody String body) {

        List<String> sightings = new ArrayList();
        List<Bird> birds = parseBirdFile();
        int id = birds.size() + 1;
        Bird bird = new Bird(id, body.toString().replace("\"", ""), sightings);
        birds.add(bird);
        upDateFile(birds, bird);
        System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " -  lajin lisäys: " + bird.getName());

        return "";
    }


    @GetMapping("/data")
    @ResponseBody
    public List<Bird> getBirds() {

        List<Bird> birds = parseBirdFile();
        if (birds.size() <= 0) {
            dummyBirds();
            birds = parseBirdFile();
        }
        return birds;
    }


    private List<Bird> dummyBirds() {
        List<String> sightings = new ArrayList();
        FileWriter file = null;
        String jsonInString = null;
        List<Bird> birds = new ArrayList<>();
        birds.add(new Bird(1, "harakka", sightings));
        birds.add(new Bird(2, "varis", sightings));
        Gson gson = new Gson();

        try {
            file = new FileWriter(filepath);
            jsonInString = gson.toJson(birds);
            file.write(jsonInString);
            file.flush();
            file.close();
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
        List<String> sightings = new ArrayList();
        try {
            id = Integer.parseInt(result.get("id").toString());
            name = result.get("name").toString();
            bird.setId(id);
            String[] sightingJson = result.get("sightings").toString().split(",");

            for (int e = 0; e < sightingJson.length; e++) {
                if (sightingJson[e].length() > 4) {
                    sightings.add(sightingJson[e].replace("[", "").replace("]", "").trim());
                }
            }
            sightings.add(new Timestamp(System.currentTimeMillis()).toString());
            bird.setName(name);
            bird.setSightings(sightings);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bird;
    }


    private List<Bird> parseBirdFile() {

        JSONParser parser = new JSONParser();
        List<String> sightings = new ArrayList();
        List<Bird> birds = new ArrayList();
        JSONArray jsonarray = null;
        org.springframework.boot.json.JsonParser springParser = JsonParserFactory.getJsonParser();
        int id = 0;
        String name;
        try {
            Object obj = parser.parse(new FileReader(filepath));

            jsonarray = (JSONArray) obj;
            for (int i = 0; i < jsonarray.size(); i++) {
                Map<String, Object> result = springParser.parseMap(jsonarray.get(i).toString());
                id = Integer.parseInt(result.get("id").toString());
                name = result.get("name").toString();
                String[] sightingsJson = result.get("sightings").toString().split(",");

                for (int e = 0; e < sightingsJson.length; e++) {
                    if (sightingsJson[e].length() > 4) {
                        sightings.add(sightingsJson[e].replace("[", "").replace("]", "").trim());
                    }
                }
                birds.add(new Bird(id, name, sightings));
                sightings = new ArrayList();
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
        int allSightings = 0;
        for (int i = 0; i < birds.size(); i++) {
            allSightings = birds.get(i).getSightings().size() + allSightings;
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


    private String reportOtherBirds(List<Bird> birds, int birdId) {
        String otherPart = "";

        for (int i = 0; i < birds.size(); i++) {
            if (birdId != birds.get(i).getId()) {
                Bird bird = birds.get(i);
                otherPart = otherPart+", " + bird.getName() + " " + bird.getSightings().size() + " kappaletta";
            }
        }

        return otherPart;
    }


}




