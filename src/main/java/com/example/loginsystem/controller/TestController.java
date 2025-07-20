package com.example.loginsystem.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Authorization,Content-Type,Accept")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testApi() {
        return "CORS Test Success!";
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String testPostApi() {
        return "CORS POST Test Success!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.PUT)
    public String testPutApi() {
        return "CORS PUT Test Success!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.DELETE)
    public String testDeleteApi() {
        return "CORS DELETE Test Success!";
    }
}