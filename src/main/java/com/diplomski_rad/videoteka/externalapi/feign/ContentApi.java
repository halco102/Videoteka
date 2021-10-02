package com.diplomski_rad.videoteka.externalapi.feign;

import com.diplomski_rad.videoteka.externalapi.model.ContentModel;
import com.diplomski_rad.videoteka.externalapi.model.MainModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "content", url = "https://data-imdb1.p.rapidapi.com/movie")
public interface ContentApi {
    @GetMapping("/id/tt0086250/")
    MainModel getMoviesByPopularity(@RequestHeader("x-rapidapi-key") String key);
}
