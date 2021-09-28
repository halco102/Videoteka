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
    private int seasons;

    public Series(String name, int year, int seasons, String imageUrl){
        super(name, year, imageUrl);
        this.seasons = seasons;
    }

}
