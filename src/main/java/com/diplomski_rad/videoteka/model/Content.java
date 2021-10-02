package com.diplomski_rad.videoteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public abstract class Content {

    @GeneratedValue(strategy = GenerationStrategy.UNIQUE) // to get unique ID's for every item that will be saved to db
    @Field
    private String id;

    @Field
    private String name;

    @Field
    private String release;

    @Field
    private Set<Creator> creators = new HashSet<>();

    @Field
    private Set<Genre> genres = new HashSet<>();

    @Field
    private int price = 10; // let it be for every content 10$

    @Field
    private String image_url;

    @Field
    private String description;

    public Content(String name, String release){
        this.name = name;
        this.release = release;
    }

    public Content(String name, String release, String imageUrl) {
        this.name = name;
        this.image_url = release;
        this.release = imageUrl;
    }

    public Content(String name, String release, String imageUrl, String description) {
        this.name = name;
        this.release = release;
        this.image_url = imageUrl;
        this.description = description;
    }

}
