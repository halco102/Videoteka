package com.diplomski_rad.videoteka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Genre {

    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @JsonProperty("genre")
    private String name;

    public Genre(String name){
        this.name = name;
    }
}
