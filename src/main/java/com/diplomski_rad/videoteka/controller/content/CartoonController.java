package com.diplomski_rad.videoteka.controller.content;

import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.exception.BadRequestException;
import com.diplomski_rad.videoteka.exception.NotFoundException;
import com.diplomski_rad.videoteka.model.Cartoon;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.content.CartoonService;
import com.diplomski_rad.videoteka.service.persons.StarsService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/videoteka")
public class CartoonController {

    @Autowired
    CartoonService cartoonService;

    @Autowired
    GenreService genreService;

    @Autowired
    StarsService starsService;

    @Autowired
    UserService userService;

    @GetMapping("/cartoons")
    public String getCartoons(Model model, String keyword, String searchGenre) {
        model.addAttribute("cartoons", cartoonService.searchEngine(searchGenre, keyword));
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("stars", starsService.getAllPersons());
        model.addAttribute("username", UserController.displayName);

        return  "videoteka/entertainment/cartoon.html";
    }

    @GetMapping("/cartoon/{id}")
    public String getCartoonById(Model model, @PathVariable String id){
        model.addAttribute("cartoons", cartoonService.getContentById(id).orElse(null));
        return "videoteka/entertainment/cartoon.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/deleteCartoon/{id}")
    public String deleteId(Model model, @PathVariable String id){
        cartoonService.deleteById(id);
        model.addAttribute("cartoons",cartoonService.getAllContent());
        return "redirect:/api/v1/videoteka/admin-add-delete/cartoon";
    }


    //update
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-add-delete/cartoon/update/{id}")
    public String editCartoon(Model model, @PathVariable String id){

        var updateCartoons = cartoonService.getContentById(id);
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);

        model.addAttribute("updateCartoons", updateCartoons);
        model.addAttribute("g",genreService.findAllGenres());
        //model.addAttribute("m",movieService.findByKeyword(keyword));
        model.addAttribute("c",cartoonService.getAllContent());


        return "videoteka/admin/edit/edit-cartoon.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin-add-delete/cartoon/update")
    public String edit(Model model,
                       @ModelAttribute("updateCartoon") Cartoon updateCartoon,
                       @RequestParam("ids") List<Genre> genres,
                       BindingResult result){

        for(int i = 0 ; i < genres.size();i++ ){
            updateCartoon.getGenres().add(genres.get(i));
        }
        cartoonService.saveContent(updateCartoon);
        return "redirect:/api/v1/videoteka/admin-add-delete/cartoon";
    }
    //end update

    //add new movie
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin-add-delete/cartoon")
    public  String addEntertainment(Model model){

        model.addAttribute("cartoons", new Cartoon());
        List<Genre> genres = new ArrayList<>();
        genreService.findAllGenres().iterator().forEachRemaining(genres::add);
        model.addAttribute("genre",genres);
        //model.addAttribute("m",movieService.findByKeyword(keyword));
        model.addAttribute("cartoon",cartoonService.getAllContent());

        return "videoteka/admin/add-cartoons.html";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin-add-delete/cartoons")
    public String submitForm( @ModelAttribute("cartoon") Cartoon cartoon,
                              @RequestParam("ids") List<Genre> genres,
                              Model model){

        for(int i = 0 ; i < genres.size();i++ ){
            cartoon.getGenres().add(genres.get(i));
        }
        cartoonService.saveContent(cartoon);
        return "redirect:/api/v1/videoteka/admin-add-delete/cartoon";
    }

    @PostMapping("/user/buy/cartoon/{id}")
    public String buyCartoon(@PathVariable("id") String id, Model model) {
        try {
            this.userService.buyContent(new Cartoon(), id);
        }catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/videoteka/errorHandling.html";
        }catch (NotFoundException not) {
            model.addAttribute("errorMessage", not.getMessage());
            return "/videoteka/errorHandling.html";
        }
        return "redirect:/api/v1/videoteka/cartoons";
    }

}
