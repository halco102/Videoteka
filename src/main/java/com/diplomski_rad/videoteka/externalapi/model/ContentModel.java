package com.diplomski_rad.videoteka.externalapi.model;

import com.diplomski_rad.videoteka.model.Genre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentModel {

    private String title;
    private String year;
    private String description;
    private int movie_length;
    private double rating;
    private String image_url;
    private String release;
    private String type;
    private String trailer;
    private Set<Genre> gen;

}
