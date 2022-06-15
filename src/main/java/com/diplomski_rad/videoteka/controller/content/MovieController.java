package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.service.content.MovieService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
@Slf4j
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @GetMapping("/movies")
    public String getMovies(Model model) {
        return this.movieService.getAllMovies(model);
    }

    @GetMapping("/movies/{id}")
    public String getMovieById(Model model, @PathVariable String id){
        return this.movieService.getMovieById(model, id);
    }

    @GetMapping("/movies/search")
    public String searchMovie(Model model, @RequestParam("keyword") String name) {
        return this.movieService.search(name, model);
    }

    @PostMapping("/content/buy/movies/{id}")
    public String buyMovie(@ModelAttribute("id") String id) {
        this.userService.buyContent(new Movie(), id);
        return "redirect:/api/v1/videoteka/movies";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/movies")
    public String getAdminPage(Model model){
        return this.movieService.getAdminPage(model);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/admin/movies")
    public String submitAdminForm(@ModelAttribute("content") @Valid Movie movies,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "ids", required = false) List<Genre> genres,
                                  Model model){

        return this.movieService.postAdminForm(movies, bindingResult, genres, model);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin/movies/update/{id}")
    public String getFormToUpdateMovie(Model model, @PathVariable String id) {
        return this.movieService.getMovieForm(model, id);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/admin/movies/delete/{id}")
    public String deleteMovieById(@PathVariable String id) {
        this.movieService.deleteById(id);
        return "redirect:/api/v1/videoteka/movies";
    }

}
