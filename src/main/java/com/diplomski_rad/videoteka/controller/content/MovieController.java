package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.MovieService;
import com.diplomski_rad.videoteka.service.persons.StarsService;

import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    GenreService genreService;

    @Autowired
    StarsService starsService;

    @GetMapping("/movies")
    public String getMovies(Model model, String keyword, String searchGenre) {
        model.addAttribute("movies", movieService.searchEngine(searchGenre, keyword));
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("stars", starsService.getAllPersons());
        model.addAttribute("userName", UserController.displayName);

        return  "videoteka/entertainment/movies.html";
    }

    @GetMapping("/movies/{id}")
    public String getMovieById(Model model, @PathVariable String id){
        model.addAttribute("movies", movieService.getContentById(id).orElse(null));
        return "videoteka/entertainment/movies.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/deleteMovie/{id}")
    public String deleteId(Model model, @PathVariable String id){
        movieService.deleteById(id);
        model.addAttribute("movies",movieService.getAllContent());
        return "redirect:/api/v1/videoteka/admin-add-delete/movies";
    }


    //update
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-add-delete/movies/update/{id}")
    public String editMovie(Model model, @PathVariable String id, String keyword){

        var updateMovies = movieService.getContentById(id);
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);

        model.addAttribute("updateMovies",updateMovies);
        model.addAttribute("g",genreService.findAllGenres());
        //model.addAttribute("m",movieService.findByKeyword(keyword));
        model.addAttribute("m",movieService.getAllContent());


        return "videoteka/admin/edit/edit-movie.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin-add-delete/movies/update")
    public String edit(Model model,
                       @ModelAttribute("updateMovies") Movie updateMovies,
                       @RequestParam("ids") List<Genre> genres,
                       BindingResult result){

        for(int i = 0 ; i < genres.size();i++ ){
            updateMovies.getGenres().add(genres.get(i));
        }
        movieService.saveContent(updateMovies);
        return "redirect:/api/v1/videoteka/admin-add-delete/movies";
    }
    //end update

    //add new movie
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-add-delete/movies")
    public  String addEntertainment(Model model,String keyword){
        Movie movies = new Movie();

        model.addAttribute("movies",movies);
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);
        model.addAttribute("g",genres);
        //model.addAttribute("m",movieService.findByKeyword(keyword));
        model.addAttribute("m",movieService.getAllContent());

        return "videoteka/admin/add-movies.html";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin-add-delete/movies")
    public String submitForm( @ModelAttribute("movies") Movie movies,
                             @RequestParam("ids") List<Genre> genres,
                             Model model){

        for(int i = 0 ; i < genres.size();i++ ){
            movies.getGenres().add(genres.get(i));
        }
        movieService.saveContent(movies);
        return "redirect:/api/v1/videoteka/admin-add-delete/movies";
    }


}
