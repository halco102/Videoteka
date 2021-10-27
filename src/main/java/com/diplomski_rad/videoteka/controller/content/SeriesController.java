package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
public class SeriesController {

    @Autowired
    SeriesService seriesService;

    @Autowired
    GenreService genreService;

    @Autowired
    UserService userService;

    @GetMapping("/series")
    public String getSeries(Model model,String keyword){
        return seriesService.getAllSeries(model, keyword);
    }

    @GetMapping("/series/search")
    public String searchSeries(Model model, @RequestParam("keyword") String name) {
        return this.seriesService.search(name, model);
    }

    @GetMapping("/series/{id}")
    public String getSeriesById(Model model, @PathVariable String id){
        return this.seriesService.getSeriesById(model, id);
    }

    @PostMapping("/content/buy/series/{id}")
    public String buySeries(@ModelAttribute("id") String id, Model model) {
        this.userService.buyContent(new Series(), id);
        return "redirect:/api/v1/videoteka/series";
    }

    @GetMapping("/admin/series")
    public String getAdminPage(Model model){
        return this.seriesService.getAdminPage(model);
    }

    @PostMapping("/admin/series")
    public String submitAdminForm(@ModelAttribute("content") @Valid Series series,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "ids", required = false) List<Genre> genres,
                                  Model model){
        return this.seriesService.postAdminForm(series, bindingResult, genres, model);
    }

    @GetMapping("/admin/series/update/{id}")
    public String getFormToUpdateSeries(Model model, @PathVariable String id) {
        model.addAttribute("content", seriesService.getContentById(id));
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Types.seriesType);
        return "videoteka/admin/admin.html";
    }

    @PostMapping("/admin/series/delete/{id}")
    public String deleteSeriesById(@PathVariable String id) {
        this.seriesService.deleteById(id);
        return "redirect:/api/v1/videoteka/series";
    }
}
