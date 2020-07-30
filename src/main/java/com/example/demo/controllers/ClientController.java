package com.example.demo.controllers;

import com.example.demo.beans.BookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@RestController
@RequestMapping("client")
@Slf4j
public class ClientController {

    public static final String URL_ENDPOINT = "https://www.googleapis.com/books/v1/volumes?q=";


    @GetMapping (value = "/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping (value = "/restTemplate")
    public BookResponse getBooks(){
        AtomicReference<BookResponse> lastResponse=new AtomicReference<>();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        IntStream.range(1, 10).forEach(
                num -> {
                    BookResponse bookResponse = restTemplate.getForObject(URL_ENDPOINT + new Random().nextInt(100), BookResponse.class);
                    lastResponse.set(bookResponse);
                    log.debug("Got {} books", bookResponse.getTotalItems());

                    Arrays.stream(bookResponse.getItems())//.filter(x -> Float.parseFloat(Optional.ofNullable(x.getVolumeInfo().getAverageRating()).orElse("0.0")) >= 2.0f)
                            .forEach(x -> log.debug("Name  {}, rating {} ", x.getVolumeInfo().getTitle(), x.getVolumeInfo().getAverageRating()));
                }
        );
        return lastResponse.get();
    }




}
