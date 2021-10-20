package com.diplomski_rad.videoteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import javax.validation.constraints.*;
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
    @NotBlank
    private String name;

    @Field
    @NotBlank
    private String release;

    @Field
    private Set<Creator> creators = new HashSet<>();

    @Field
    private Set<Genre> genres = new HashSet<>();

    @Field
    @Min(1)
    @Max(100)
    private Integer price;

    @Field
    @NotBlank
    private String image_url;

    @Field
    @NotBlank
    private String description;

    @Field
    @NotBlank
    private String trailer;

    @Field
    @DecimalMin(value = "0.0", message = "Minimal rating is 0.0")
    @DecimalMax(value = "10.0", message = "Maximal rating is 10.0")
    private double rating;

    private String content_rating;

    public Content(String name, String release){
        this.name = name;
        this.release = release;
    }

    public Content(String name, String release, String imageUrl) {
        this.name = name;
        this.image_url = release;
        this.release = imageUrl;
    }

    public Content(String name, String release, String imageUrl, String description, String trailer, double rating, Set<Genre> genres) {
        this.name = name;
        this.release = release;
        this.image_url = imageUrl;
        this.description = description;
        this.trailer = trailer;
        this.rating = rating;
        this.genres = genres;
    }

    public Content(String name, String release, String imageUrl, String description, String trailer, double rating, Set<Genre> genres, String content_rating) {
        this.name = name;
        this.release = release;
        this.image_url = imageUrl;
        this.description = description;
        this.trailer = trailer;
        this.rating = rating;
        this.genres = genres;
        this.content_rating = content_rating;
    }

}
