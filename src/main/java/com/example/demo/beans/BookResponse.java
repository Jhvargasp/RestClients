package com.example.demo.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponse {
    private String totalItems;

    private String kind;

    private Items[] items;


}