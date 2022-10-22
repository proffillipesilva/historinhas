package com.fiec.lpiiiback.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/{someone}")
    public String sayHelloToSomeone(@PathVariable("someone") String someone){
        return "Hello World to " + someone;
    }
}
