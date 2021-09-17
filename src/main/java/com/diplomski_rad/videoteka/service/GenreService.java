package com.diplomski_rad.videoteka.service;

import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public Genre findGenreByName(String name) {
        var genre = genreRepository.findGenreByName(name);

        if(genre.isPresent()) {
            return genre.get();
        }
        return null;
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findGenreById(String id) {
        return genreRepository.findById(id).orElseThrow();
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }


}
