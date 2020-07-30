package com.example.demo.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VolumeInfo {
    //private IndustryIdentifiers[] industryIdentifiers;

    private String printType;

    //private ReadingModes readingModes;

    private String previewLink;

    private String canonicalVolumeLink;

    private String language;

    private String title;

    //private ImageLinks imageLinks;

    private String averageRating;

    private String publisher;

    private String ratingsCount;

    private String maturityRating;

    private String allowAnonLogging;

    private String contentVersion;

    private String infoLink;
}
