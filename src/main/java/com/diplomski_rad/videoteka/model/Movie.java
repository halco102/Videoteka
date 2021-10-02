package com.diplomski_rad.videoteka.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Movie extends Content{

    @Field
    private int runtime;

    @Field
    private Set<Stars> stars = new HashSet<>();

    public Movie(String name, String release, int runtime, String imageUrl){
        super(name, release, imageUrl);
        this.runtime = runtime;
    }

    public Movie(String name, String release, int runtime, String image_url, String description, String trailer, double rating, Set<Genre> genres) {
        super(name, release, image_url, description, trailer, rating, genres);
        this.runtime = runtime;
    }

}
