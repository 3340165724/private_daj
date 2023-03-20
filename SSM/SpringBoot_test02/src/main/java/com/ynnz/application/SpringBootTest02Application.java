package com.ynnz.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootTest02Application {

    @RequestMapping("/springboot")
    public String hello(){
        return "hello spring boot";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTest02Application.class, args);
    }

}
