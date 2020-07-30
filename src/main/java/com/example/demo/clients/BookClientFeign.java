package com.example.demo.clients;


import com.example.demo.beans.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "books",url = "https://www.googleapis.com")
public interface BookClientFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/books/v1/volumes?q={data}")
    BookResponse getBooks(@RequestParam("data") int data);
}