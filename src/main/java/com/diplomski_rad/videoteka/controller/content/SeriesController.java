package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import com.diplomski_rad.videoteka.service.persons.StarsService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getSeries(Model model,String keyword){
        model.addAttribute("contents",seriesService.getAllContent());
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("links", seriesService.getType(Types.seriesType));
        model.addAttribute("search", seriesService.searchEngine(keyword));

        return  "videoteka/entertainment/main/main.html";
    }

    @GetMapping("/series/{id}")
    public String getSeriesById(Model model, @PathVariable String id){
        model.addAttribute("content",seriesService.getContentById(id).get());
        model.addAttribute("title", Types.seriesType);
        return "videoteka/entertainment/single_page.html";
    }

    @PostMapping("/content/buy/series/{id}")
    public String buySeries(@ModelAttribute("id") String id, Model model) {
        this.userService.buyContent(new Series(), id);
        return "redirect:/api/v1/videoteka/series";
    }

    @GetMapping("/admin/series")
    public String getAdminPage(Model model){
        model.addAttribute("content", new Series());
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/admin.html";
    }

    @PostMapping("/admin/series")
    public String submitAdminForm(@ModelAttribute("content") Series series, @RequestParam(name = "ids", required = false)List<Genre> genres){
        return this.seriesService.submitAdminForm(series, genres);
    }

    @GetMapping("/admin/series/update/{id}")
    public String getFormToUpdateSeries(Model model, @PathVariable String id) {
        model.addAttribute("content", seriesService.getContentById(id));
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Types.seriesType);
        return "videoteka/admin/admin.html";
    }

    @PostMapping("/admin/series/delete/{id}")
    public String deleteMovieById(@PathVariable String id) {
        this.seriesService.deleteById(id);
        return "redirect:/api/v1/videoteka/series";
    }
}
