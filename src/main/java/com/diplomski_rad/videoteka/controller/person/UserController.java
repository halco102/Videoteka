package com.diplomski_rad.videoteka.controller.person;

import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.service.persons.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/videoteka")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    public static String displayName;

    @GetMapping("/index")
    public  String getIndex(Model model){
        return userService.getIndexPage(model);
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
        return "videoteka/login/login_page.html";
    }


    @PostMapping("/login/post")
    public String loginToPage(@ModelAttribute("users")  User user, Model model) throws NotFoundException {
        return userService.submitLogin(user, model, getIndex(model));
    }


    @GetMapping("/register")
    public String createAccount(Model model){
        User user = new User();
        model.addAttribute("users",user);
        return "videoteka/login/create-account.html";
    }


    @PostMapping("/register")
    public String createAccount(@ModelAttribute("users") @Valid User user,
                                BindingResult result,
                                Model model){

        if (result.hasErrors()) {
            return "videoteka/login/create-account.html";
        }
        else if(this.userService.validation(user)==true){

            this.userService.saveUser(user);
            return "redirect:/api/v1/videoteka/login";

        }

        return "videoteka/login/create-account.html";

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
        model.addAttribute("users", this.userService.getUserProfile());
        model.addAttribute("username", displayName);

        return "videoteka/profile/profile.html";
    }


}
