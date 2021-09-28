package com.diplomski_rad.videoteka.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cartoon extends Content{

    @Field
    private int seasons;

    public Cartoon(String name, int year, int seasons, String imageUrl){
        super(name, year, imageUrl);
        this.seasons = seasons;
    }

}
