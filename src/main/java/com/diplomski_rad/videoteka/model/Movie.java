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
public class Movie extends Content{

    @Field
    private int runtime;

    @Field
    private Set<Stars> stars = new HashSet<>();

    public Movie(String name, String release, int runtime, String imageUrl){
        super(name, release, imageUrl);
        this.runtime = runtime;
    }

}
