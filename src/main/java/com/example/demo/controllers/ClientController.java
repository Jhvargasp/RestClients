package com.example.demo.controllers;


import com.example.demo.beans.BookResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@RestController
@RequestMapping("client")
@Slf4j
public class ClientController {

    public static final String URL_ENDPOINT = "https://www.googleapis.com/books/v1/volumes?q=";


    @GetMapping (value = "/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping (value = "/stream")
    public BookResponse stream() throws IOException {
        Gson g = new Gson();

        BookResponse bookResponse=null;
        StringBuffer response=new StringBuffer();
        URL urlForGetRequest = new URL(URL_ENDPOINT+ new Random().nextInt(100));

        String readLine = null;

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

        conection.setRequestMethod("GET");

        //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here

        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(

                    new InputStreamReader(conection.getInputStream()));



            while ((readLine = in .readLine()) != null) {

                response.append(readLine);

            } in .close();

            // print result

            log.debug("JSON String Result " + response.toString());
            bookResponse=g.fromJson(response.toString(), BookResponse.class);


        } else {

            log.error("GET NOT WORKED");

        }
        return bookResponse;
    }





}
