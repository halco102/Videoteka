package com.diplomski_rad.videoteka.restcontroller;

import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.service.content.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MoviesApi {

    @Autowired
    MovieService movieService;


    @GetMapping()
    public ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<>(this.movieService.getAllContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable String id) {
        log.info(String.valueOf(this.movieService.getContentById(id).get()));
        return new ResponseEntity<>(this.movieService.getContentById(id).get(), HttpStatus.OK);
    }

    // uraditi da se nadju sve genres, i na osnovu toga filtrirati koji su svi filmovi sa tim zanrom
/*    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenresFromMovies() {
        return new ResponseEntity<>(this.movieService.getGenresByContent(Movie.class), HttpStatus.OK);
    }*/

    // get popular movies
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularMovies() {
        return new ResponseEntity<>(this.movieService.getPopularContent(Movie.class), HttpStatus.OK);
    }

}
