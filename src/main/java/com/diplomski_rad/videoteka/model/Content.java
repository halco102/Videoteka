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
    private int year;

    @Field
    private Set<Creator> creators = new HashSet<>();

    @Field
    private Set<Genre> genres = new HashSet<>();

    public Content(String name, int year){
        this.name = name;
        this.year = year;
    }

}
