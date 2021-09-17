package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService extends AbstractContentService<Movie>{

    @Autowired
    MovieRepository movieRepository;

    public MovieService(AbstractContentRepo<Movie> abstractContentRepo) {
        super(abstractContentRepo);
    }

    @Override
    public List<Movie> getAllContent() {
        return super.getAllContent();
    }

    @Override
    public Optional<Movie> getContentById(String id) {
        return super.getContentById(id);
    }

    @Override
    public Movie saveContent(Movie entitiy) {
        return super.saveContent(entitiy);
    }

    @Override
    public Movie updateById(String id, Movie entity) {
        return super.updateById(id, entity);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public Movie getByName(String name) {
        return super.getByName(name);
    }

    @Override
    public List<Movie> findByKeyword(String keyword) {
        return super.findByKeyword(keyword);
    }

    @Override
    public List<Movie> searchEngine(String searchGenre, String keyword) {
        return super.searchEngine(searchGenre, keyword);
    }
}
