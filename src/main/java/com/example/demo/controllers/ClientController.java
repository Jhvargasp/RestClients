package com.example.demo.controllers;

import com.example.demo.beans.BookResponse;
import com.example.demo.clients.BookClientFeign;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("client")
@Slf4j
public class ClientController {

    public static final String URL_ENDPOINT = "https://www.googleapis.com/books/v1/volumes?q=";


    @GetMapping(value = "/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(value = "/stream")
    public BookResponse stream() {
        Gson g = new GsonBuilder().setLenient().create();

        AtomicReference<BookResponse> bookResponse = new AtomicReference<>();
        StringBuffer response = new StringBuffer();
        try {
            callUsingSream(g, bookResponse, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookResponse.get();
    }

    private void callUsingSream(Gson g, AtomicReference<BookResponse> bookResponse, StringBuffer response) throws IOException {
        URL urlForGetRequest = new URL(URL_ENDPOINT + new Random().nextInt(100));

        String readLine = null;

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();

        conection.setRequestMethod("GET");

        //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here

        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(

                    new InputStreamReader(conection.getInputStream()));


            while ((readLine = in.readLine()) != null) {

                response.append(readLine);

            }
            in.close();

            // print result

            log.debug("JSON String Result " + response.toString());
            try {
                bookResponse.set(g.fromJson(response.toString(), BookResponse.class));
            } catch (RuntimeException ex) {
                log.error("Problem translating data.. GSON.. {}", ex.getMessage());
            }

        } else {

            log.error("GET NOT WORKED");

        }
    }


    @GetMapping(value = "/restTemplate")
    public BookResponse getBooks() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        BookResponse bookResponse = restTemplate.getForObject(URL_ENDPOINT + new Random().nextInt(100), BookResponse.class);
        log.debug("Got {} books", bookResponse.getTotalItems());

        Arrays.stream(bookResponse.getItems())//.filter(x -> Float.parseFloat(Optional.ofNullable(x.getVolumeInfo().getAverageRating()).orElse("0.0")) >= 2.0f)
                .forEach(x -> log.debug("Name  {}, rating {} ", x.getVolumeInfo().getTitle(), x.getVolumeInfo().getAverageRating()));
        return bookResponse;
    }

    @GetMapping(value = "/webClient")
    public Mono<BookResponse> getBooks2() {
        WebClient client2 = WebClient.create(URL_ENDPOINT + new Random().nextInt(100));
        Mono<BookResponse> monoBookResponse = client2.get().retrieve().bodyToMono(BookResponse.class);
        monoBookResponse.subscribe(
                x -> Arrays.stream(x.getItems()).forEach(y -> log.debug("Name  {}, rating {} ", y.getVolumeInfo().getTitle(), y.getVolumeInfo().getAverageRating()))
        );
        return monoBookResponse;
    }

    @Autowired
    BookClientFeign feignClient;

    @GetMapping(value = "/feign")
    public BookResponse getBooks3() {
        BookResponse bookResponse = feignClient.getBooks(new Random().nextInt(100));
        log.debug("Got {} books", bookResponse.getTotalItems());

        Arrays.stream(bookResponse.getItems())//.filter(x -> Float.parseFloat(Optional.ofNullable(x.getVolumeInfo().getAverageRating()).orElse("0.0")) >= 2.0f)
                .forEach(x -> log.debug("Name  {}, rating {} ", x.getVolumeInfo().getTitle(), x.getVolumeInfo().getAverageRating()));
        return bookResponse;
    }

}
