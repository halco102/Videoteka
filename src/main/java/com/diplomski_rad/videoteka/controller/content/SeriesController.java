package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import com.diplomski_rad.videoteka.service.persons.StarsService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
public class SeriesController {

    @Autowired
    SeriesService seriesService;

    @Autowired
    GenreService genreService;

    @Autowired
    StarsService starsService;

    @Autowired
    UserService userService;

    @GetMapping("/series")
    public String getSeries(Model model,String keyword,String searchGenre){
        model.addAttribute("series",seriesService.searchEngine(searchGenre,keyword));
        model.addAttribute("genres",genreService.findAllGenres());
        model.addAttribute("stars",starsService.getAllPersons());
        model.addAttribute("username", UserController.displayName);

        return "videoteka/entertainment/series.html";
    }

    @GetMapping("series/{id}")
    public String getSeriesById(Model model, @PathVariable String id){
        model.addAttribute("series",seriesService.getContentById(id).get());
        return "videoteka/entertainment/series.html";
    }

    //for admin
    @GetMapping("/delete-series/{id}")
    public String deleteId(Model model, @PathVariable String id){
        seriesService.deleteById(id);
        model.addAttribute("series",seriesService.getAllContent());
        return "redirect:/api/v1/videoteka/admin-add-delete/series";
    }

    @GetMapping("/admin-add-delete/series")
    public  String addEntertainment(Model model,String keyword){
        Series series= new Series();
        model.addAttribute("series",series);
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);
        model.addAttribute("g",genres);
        model.addAttribute("se",seriesService.findByKeyword(keyword));

        return "videoteka/admin/add-series.html";
    }


    //update
    @GetMapping("/admin-add-delete/series/update/{id}")
    public String editSeries(Model model, @PathVariable String id, Movie movie, String keyword){
        Series updateSeries = seriesService.getContentById(id).get();
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);
        model.addAttribute("updateSeries", updateSeries);
        model.addAttribute("g",genreService.findAllGenres());
        model.addAttribute("m",seriesService.findByKeyword(keyword));

        return "videoteka/admin/edit/edit-series.html";
    }



    @PostMapping("/admin-add-delete/series/update")
    public String submitForm(@ModelAttribute("updateSeries") Series series,
                             @RequestParam("ids") List<Genre> genres,
                             Model model){

        for(int i = 0 ; i < genres.size();i++ ){
            series.getGenres().add(genres.get(i));
        }
        seriesService.saveContent(series);
        return "redirect:/api/v1/videoteka/admin-add-delete/series";
    }

    @PostMapping("/user/buy/series/{id}")
    public String buyMovie(@ModelAttribute("id") String id) {
        this.userService.buyContent(new Series(), id);
        return "redirect:/api/v1/videoteka/series";
    }
}
