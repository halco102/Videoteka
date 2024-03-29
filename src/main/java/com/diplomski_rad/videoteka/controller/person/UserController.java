package com.diplomski_rad.videoteka.controller.person;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.model.CountryEnum;
import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.service.content.MovieService;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/videoteka")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;
    @Autowired
    SeriesService seriesService;

    public static String displayName;

    @GetMapping("/index")
    public String getIndex(Model model){
        return userService.getIndexPage(model, movieService, seriesService);
    }

    @GetMapping("")
    public String getLogin(){
        return "redirect:/api/v1/videoteka/login";
    }


    @GetMapping("/login")
    public String loginToPage(Model model){
        User user = new User();
        model.addAttribute("users",user);
       // return "videoteka/login/sign-in.html";
        return "videoteka/login/signin.html";
    }


    @PostMapping("/login/post")
    public String loginToPage(@ModelAttribute("users")  User user, Model model) throws NotFoundException {
        return userService.submitLogin(user, model, getIndex(model));
    }


    @GetMapping("/register")
    public String createAccount(Model model){
        model.addAttribute("users",new User());

        model.addAttribute("countries", CountryEnum.values());
        return "videoteka/login/signup.html";
    }


    @PostMapping("/register")
    public String createAccount(@ModelAttribute("users") @Valid User user,
                                BindingResult result,
                                Model model,
                                @RequestParam("country") String country){

        if (result.hasErrors()) {
            model.addAttribute("countries", CountryEnum.values());
            return "videoteka/login/signup.html";
        }
        else if(this.userService.validation(user)){
            this.userService.saveUser(user, country);
            return "videoteka/login/signin.html";
        }

        return "videoteka/login/signup.html";

    }//end createAccount method

    @GetMapping("/logout")
    public String logoutController(Model model){
        model.addAttribute("null","null");
        UserService.jwtLoggedUser = null;
        SecurityContextHolder.clearContext();
        return "redirect:/api/v1/videoteka/login";
    }

    @GetMapping("/user/profile")
    public String getUserProfile(Model model) {
        model.addAttribute("user", this.userService.getUserProfile());
        model.addAttribute("col", 2);
        model.addAttribute("username", displayName);

        return "videoteka/profile/profile.html";
    }



}
