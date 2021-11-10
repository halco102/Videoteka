package com.diplomski_rad.videoteka.externalapi.model;

import com.diplomski_rad.videoteka.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreResult {

    private List<GenreModel> results;

}
