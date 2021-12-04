package com.diplomski_rad.videoteka.restcontroller.content;

import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/series")
public class SeriesApi {

    @Autowired
    SeriesService seriesService;

    @GetMapping()
    public ResponseEntity<?> getAllSeries() {
        return new ResponseEntity<>(this.seriesService.getAllContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllSeriesById (@PathVariable String id) {
        return new ResponseEntity<>(this.seriesService.getContentById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getAllPopularSeries () {
        return new ResponseEntity<>(this.seriesService.getPopularContent(Series.class), HttpStatus.OK);
    }

}
