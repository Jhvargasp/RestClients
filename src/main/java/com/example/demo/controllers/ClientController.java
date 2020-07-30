package com.example.demo.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
@Slf4j
public class ClientController {

    public static final String URL_ENDPOINT = "https://www.googleapis.com/books/v1/volumes?q=";


    @GetMapping (value = "/hello")
    public String helloWorld(){
        return "Hello World!";
    }






}
