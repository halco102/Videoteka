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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/videoteka")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    public static String displayName;

    @GetMapping("/index")
    public  String getIndex(Model model){

        //check koji user je loged in trenutno
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        displayName = authentication.getPrincipal().toString();

        if (displayName.matches("anonymousUser")) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else if (displayName.matches("Admin")) {
            return "redirect:/api/v1/videoteka/admin-add-delete/movies";
        }

        model.addAttribute("userName", authentication.getPrincipal().toString());

        return "videoteka/index.html";
    }

    @GetMapping("")
    public String getLogin(){
        return "redirect:/api/v1/videoteka/login";
    }


    @GetMapping("/login")
    public String loginToPage(Model model){
        User user = new User();
        model.addAttribute("users",user);
        return "videoteka/login/sign-in.html";
    }


    @PostMapping("/login/post")
    public String loginToPage(@ModelAttribute("users")  User user,
                              BindingResult result,
                              Model model,
                              Error error) throws NotFoundException {

        try {
            if(userService.login(user.getUsername(), user.getPassword()) != null) {
                if (getIndex(model) != null) {
                    return "redirect:/api/v1/videoteka/index";
                }
            }
        }catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "videoteka/errorHandling.html";
        }

        model.addAttribute("errorMessage", "unknown error ocured");
        return "videoteka/errorHandling.html";
    }


    @GetMapping("/register")
    public String createAccount(Model model){
        User user = new User();
        model.addAttribute("users",user);
        return "videoteka/login/create-account.html";
    }


    @PostMapping("/register")
    public String createAccount(@ModelAttribute("users")  User user,
                                BindingResult result,
                                Model model,
                                Error error){

        if (result.hasErrors()) {
            return "videoteka/login/create-account.html";
        }
        else if(this.userService.validation(user)==true){

            // user.getRoleSet().add(this.roleService.findByRole(2L)); //test purpose when using Bootstrap!!
            this.userService.saveUser(user);
            return "redirect:/api/v1/videoteka/login";

        }

        return "videoteka/login/create-account.html";

    }//end createAccount method

    @GetMapping("/logout")
    public String logoutController(){
        UserService.jwtLoggedUser = null;
        return "videoteka/login/sign-in.html";
    }

}
