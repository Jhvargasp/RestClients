package com.example.demo.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Items {
    //private SaleInfo saleInfo;

    //private SearchInfo searchInfo;

    private String kind;

    private VolumeInfo volumeInfo;

    private String etag;

    private String id;

    //private AccessInfo accessInfo;

    private String selfLink;

}
