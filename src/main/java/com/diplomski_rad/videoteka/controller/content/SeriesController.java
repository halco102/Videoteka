package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.constants.Titles;
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
        model.addAttribute("title", Titles.seriesType);
        model.addAttribute("links", seriesService.getType(Titles.seriesType));
        model.addAttribute("search", seriesService.searchEngine(keyword));

        return  "videoteka/entertainment/main/main.html";
    }

    @GetMapping("/series/{id}")
    public String getSeriesById(Model model, @PathVariable String id){
        model.addAttribute("content",seriesService.getContentById(id).get());
        model.addAttribute("title", Titles.seriesType);
        return "videoteka/entertainment/test2.html";
    }

    @PostMapping("/user/buy/series/{id}")
    public String buySeries(@ModelAttribute("id") String id, Model model) {
        this.userService.buyContent(new Series(), id);
        return "redirect:/api/v1/videoteka/series";
    }

    @GetMapping("/admin/series")
    public String getAdminPage(Model model){
        model.addAttribute("content", new Series());
        model.addAttribute("title", Titles.seriesType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/testAdmin.html";
    }

    @PostMapping("/admin/series")
    public String submitAdminForm(@ModelAttribute("content") Series series, @RequestParam(name = "ids", required = false)List<Genre> genres){
        return this.seriesService.submitAdminForm(series, genres);
    }

    @GetMapping("/admin/series/update/{id}")
    public String getFormToUpdateSeries(Model model, @PathVariable String id) {
        model.addAttribute("content", seriesService.getContentById(id));
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Titles.seriesType);
        return "videoteka/admin/testAdmin.html";
    }

    @PostMapping("/admin/series/delete/{id}")
    public String deleteMovieById(@PathVariable String id) {
        this.seriesService.deleteById(id);
        return "redirect:/api/v1/videoteka/series";
    }

        /*//for admin
    @GetMapping("/delete-series/{id}")
    public String deleteId(Model model, @PathVariable String id){
        seriesService.deleteById(id);
        model.addAttribute("series",seriesService.getAllContent());
        return "redirect:/api/v1/videoteka/admin-add-delete/series";
    }

    @GetMapping("/admin-add-delete/series")
    public  String addEntertainment(Model model){
        Series series= new Series();
        model.addAttribute("series",series);
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);
        model.addAttribute("g",genres);
        model.addAttribute("se",seriesService.getAllContent());

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin-add-delete/series/update")
    public String edit(Model model,
                       @ModelAttribute("updateSeries") Series updateSeries,
                       @RequestParam("ids") List<Genre> genres,
                       BindingResult result){

        for(int i = 0 ; i < genres.size();i++ ){
            updateSeries.getGenres().add(genres.get(i));
        }
        seriesService.saveContent(updateSeries);
        return "redirect:/api/v1/videoteka/admin-add-delete/series";
    }



    @PostMapping("/admin-add-delete/series")
    public String submitForm(@ModelAttribute("series") Series series,
                             @RequestParam("ids") List<Genre> genres,
                             Model model){

        for(int i = 0 ; i < genres.size();i++ ){
            series.getGenres().add(genres.get(i));
        }
        seriesService.saveContent(series);
        return "redirect:/api/v1/videoteka/admin-add-delete/series";
    }*/

}
