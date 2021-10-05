package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.constants.Titles;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.MovieService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
@Slf4j
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    GenreService genreService;

    @Autowired
    UserService userService;

    @GetMapping("/movies")
    public String getMovies(Model model, String keyword, String searchGenre) {
        model.addAttribute("contents", movieService.searchEngine(searchGenre, keyword));
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Titles.movieType);
        model.addAttribute("links", movieService.getType(Titles.movieType));

        return  "videoteka/entertainment/test.html";
    }

    @GetMapping("/movies/{id}")
    public String getMovieById(Model model, @PathVariable String id){
        model.addAttribute("content", movieService.getContentById(id).orElse(null));
        model.addAttribute("title", Titles.movieType);
        model.addAttribute("username", UserController.displayName);
        return "videoteka/entertainment/test2.html";
    }


    @PostMapping("/user/buy/movies/{id}")
    public String buyMovie(@ModelAttribute("id") String id, Model model) {
        this.userService.buyContent(new Movie(), id);
        return "redirect:/api/v1/videoteka/movies";
    }

    //novi admin endpoints, oni stari su bljak
    @GetMapping("/admin/movies")
    public String getAdminPage(Model model){
        model.addAttribute("content", new Movie());
        model.addAttribute("title", Titles.movieType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/testAdmin.html";
    }

    @PostMapping("/admin/movies")
    public String submitAdminForm(@ModelAttribute("content") @Valid Movie movies,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "ids", required = false) List<Genre> genres,
                                  Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("title", Titles.movieType);
            return "videoteka/admin/testAdmin.html";
        }
        return this.movieService.submitAdminForm(movies, genres);
    }

    @GetMapping("/admin/movies/update/{id}")
    public String getFormToUpdateMovie(Model model, @PathVariable String id) {
        var movie = movieService.getContentById(id);
        model.addAttribute("content", movie.get());
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Titles.movieType);
        return "videoteka/admin/testAdmin.html";
    }

    @PostMapping("/admin/movies/delete/{id}")
    public String deleteMovieById(@PathVariable String id) {
        this.movieService.deleteById(id);
        return "redirect:/api/v1/videoteka/movies";
    }

/*    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public  String addEntertainment(Model model){
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
    public String submitForm(@ModelAttribute("movies") Movie movies,
                             @RequestParam("ids") List<Genre> genres,
                             Model model){

        for(int i = 0 ; i < genres.size();i++ ){
            movies.getGenres().add(genres.get(i));
        }
        movieService.saveContent(movies);
        return "redirect:/api/v1/videoteka/admin-add-delete/movies";
    }*/

}
