package com.diplomski_rad.videoteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Series extends Content{

    @Field
    private Set<Stars> stars = new HashSet<>();

    @Field
    private int runtime;

    @Field
    private int seasons;

    public Series(String name, String release, int seasons, String imageUrl){
        super(name, release, imageUrl);
        this.seasons = seasons;
    }

    public Series(String name, String release, String imageUrl, String description, String trailer, double rating, Set<Genre> genres, int runtime, String content_rating) {
        super(name, release, imageUrl, description, trailer, rating, genres, content_rating);
        this.runtime = runtime;
    }
}
