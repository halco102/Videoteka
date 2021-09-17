package com.diplomski_rad.videoteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DependsOn
public class Role {

    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
