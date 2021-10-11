package com.diplomski_rad.videoteka.externalapi.controller;

import com.diplomski_rad.videoteka.externalapi.feign.ContentApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
public class ExternalApi {
    @Value("${apiKey}")
    String apiKey;

    @Autowired
    ContentApi contentApi;

    @GetMapping("/test/{id}")
    public ResponseEntity<?> testing(@PathVariable String id){
        return new ResponseEntity<>(this.contentApi.getMoviesByPopularity(apiKey, id), HttpStatus.OK);
    }

}
