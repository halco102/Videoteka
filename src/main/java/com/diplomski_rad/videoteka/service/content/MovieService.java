package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import com.diplomski_rad.videoteka.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService extends AbstractContentService<Movie>{

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreService genreService;

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
    public List<Movie> searchEngine(String keyword) {
        return super.searchEngine(keyword);
    }


    public String submitAdminForm(Movie movies, List<Genre> genres) {

        if (movies.getId() != null) {
            var oldMovie = movieRepository.findById(movies.getId()).get();

            if (genres != null) {
                movies.getGenres().clear();
                movies.getGenres().addAll(genres);
            }else {
                movies.getGenres().addAll(oldMovie.getGenres());
            }

            movieRepository.save(movies);
            return "redirect:/api/v1/videoteka/movies";
        }
        movieRepository.save(movies);
        return "redirect:/api/v1/videoteka/admin/movies";
    }

    //movies
    public String getAllMovies(Model model, String keyword){
        model.addAttribute("contents", searchEngine(keyword));
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Types.movieType);
        model.addAttribute("links", getType(Types.movieType));
        model.addAttribute("search", searchEngine(keyword));

        return  "videoteka/entertainment/main/main.html";
    }

    public String getMovieById(Model model, String id) {
        model.addAttribute("content", getContentById(id).orElse(null));
        model.addAttribute("title", Types.movieType);
        model.addAttribute("username", UserController.displayName);
        return "videoteka/entertainment/single_page.html";
    }

    public String getAdminPage(Model model){
        model.addAttribute("content", new Movie());
        model.addAttribute("title", Types.movieType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/admin.html";
    }

    public String postAdminForm(Movie movies,
                                  BindingResult bindingResult,
                                  List<Genre> genres,
                                  Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("title", Types.movieType);
            model.addAttribute("genres", genreService.findAllGenres());
            return "videoteka/admin/admin.html";
        }
        return submitAdminForm(movies, genres);
    }

    public String getMovieForm(Model model, String id) {
        var movie = getContentById(id);
        model.addAttribute("content", movie.get());
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Types.movieType);
        return "videoteka/admin/admin.html";
    }



}
