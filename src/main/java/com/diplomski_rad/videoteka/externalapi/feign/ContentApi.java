package com.diplomski_rad.videoteka.externalapi.feign;

import com.diplomski_rad.videoteka.externalapi.model.ContentModel;
import com.diplomski_rad.videoteka.externalapi.model.GenreResult;
import com.diplomski_rad.videoteka.externalapi.model.MainModel;
import com.diplomski_rad.videoteka.model.Genre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Set;

@FeignClient(name = "content", url = "https://data-imdb1.p.rapidapi.com")
public interface ContentApi {

    @GetMapping("/movie/id/{id}/")
    MainModel getMovieByImdbId(@RequestHeader("x-rapidapi-key") String key, @PathVariable String id);

    @GetMapping("/series/id/{id}/")
    MainModel getSeriesByImdbId(@RequestHeader("x-rapidapi-key") String key, @PathVariable String id);

    @GetMapping("/genres/")
    GenreResult getAllGenres(@RequestHeader("x-rapidapi-key") String key);

}
