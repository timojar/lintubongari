package com.example.birdchannel.controller;

import com.example.birdchannel.model.Bird;
import com.example.birdchannel.model.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a1500863 on 11.4.2018.
 */


@Controller
public class Test {

    @GetMapping("/test")
    @ResponseBody
    public List<Bird> showBird() {


        List<Bird> birds = dummyBirds();


        return birds;
    }


    private List<Bird> dummyBirds() {


        List<Bird> birds = new ArrayList();

        birds.add(new Bird(1, "talitintti"));
        birds.add(new Bird(2, "varis"));
        birds.add(new Bird(3, "lokki"));


        return birds;


    }


}
