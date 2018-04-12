package com.example.birdchannel.controller;

import com.example.birdchannel.model.Bird;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by a1500863 on 11.4.2018.
 */



@Controller
public class Test {

    @GetMapping("/test")
    @ResponseBody
 public Bird showBird(){




     return new Bird(1,"talitintti");
 }



}
