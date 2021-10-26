package com.diplomski_rad.videoteka.service.persons;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.exception.BadRequestException;
import com.diplomski_rad.videoteka.exception.NotFoundException;
import com.diplomski_rad.videoteka.model.Cartoon;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SigninRequest;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.payload.response.BoughtContent;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import com.diplomski_rad.videoteka.repository.content.SeriesRepository;
import com.diplomski_rad.videoteka.repository.person.UserRepository;
import com.diplomski_rad.videoteka.service.content.MovieService;
import com.diplomski_rad.videoteka.service.content.SeriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends AbstractPersonService<User> {

    private final String avatarUrl = "https://avatars.dicebear.com/api/bottts/";

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final FusionAuth fusionAuth;

    private final MovieRepository movieRepository;

    private final SeriesRepository seriesRepository;

    public static String jwtLoggedUser;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FusionAuth fusionAuth, MovieRepository movieRepository, SeriesRepository seriesRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fusionAuth = fusionAuth;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
    }

    @Override
    public Optional<User> getById(String id) {
        return super.getById(id);
    }

    @Override
    public List<User> getAllPersons() {
        return super.getAllPersons();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    public static String username;

    private final String appId = "c155033d-8a15-4bea-ad87-b57bc4d65d64"; // posto imam samo 1 tenant i 1 app u tenantu dovoljno je hardcodirati appId


    private String randomUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    public User saveUser(User user) {

        SignupRequest signupRequest = new SignupRequest();

        signupRequest.setAppId(appId);
        signupRequest.setEmail(user.getEMail());
        signupRequest.setUsername(user.getUsername());
        signupRequest.setPassword(user.getPassword());

        var temp = fusionAuth.signUp(signupRequest);

        //dodaj role useru iz fa
        user.setRoles(fusionAuth.getUserRolesFromApp(appId, temp.getId()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setMoney(200); // always give 200$
        user.setAvatar(avatarUrl + randomUUID() + ".svg");

        return userRepository.save(user);
    }

    public boolean checkIfUsernameExists(String username){
        var user = userRepository.checkIfUserExists(username);
        if(user.isPresent()){
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email) {
        var user = userRepository.checkEmail(email);
        if(user.isPresent()) {
            return true;
        }
        return false;
    }

    public Optional<User> findUserByUsername(String username) {
       var user = userRepository.checkIfUserExists(username);
       if(user.isPresent()) {
           return user;
       }
       return null;
    }

    public User login(String username, String password) {

        SigninRequest signinRequest = new SigninRequest();
        var temp = userRepository.checkIfUserExists(username);

        if(temp.isEmpty()) {
           throw new NotFoundException("The user was not found");
        }

        signinRequest.setEmail(temp.get().getEMail());
        signinRequest.setPassword(password);
        signinRequest.setAppId(appId);
        var fa = fusionAuth.signIn(signinRequest);

        if (fa != null) {

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    temp.get().getUsername(),
                    temp.get().getPassword()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = new User();
            user.setUsername(fa.getUser().getUsername());
            user.setEMail(fa.getUser().getEMail());

            jwtLoggedUser = fa.getToken();

            return user;
        }

        return null;
    }

    public User checkIfUserIsInDatabase(String username, String password){
        var user = userRepository.checkIfUserIsInDatabase(username, password);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public boolean exists(String username, String password){
        var user = userRepository.checkIfUserIsInDatabase(username, password);
        if(user.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean validation(User user){
        if(!user.getUsername().isEmpty() || !user.getPassword().isEmpty() || !user.getEMail().isEmpty() || !user.getFirstName().isEmpty()) {
            if (checkIfUsernameExists(user.getUsername()) == false
                    && checkEmail(user.getEMail()) == false
                    && checkIfPasswordMach(user.getPassword(), user.getConfirmPassword()) == true) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkIfPasswordMach(String password, String repeatPassword) {
        if(password.equals(repeatPassword)){
            return true;
        }else {
            return false;
        }
    }

    public User getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal().toString().matches("anonymousUser")){
            return null;
        }

        var user = findUserByUsername(authentication.getPrincipal().toString());

        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }



    private boolean ifUserBoughtContent (List<BoughtContent> list, String id) {

        if(list == null || list.isEmpty()) {
            // let it add first item to list
            return false;
        }


            // map to movie
            if (list.stream()
                    .filter(in -> in.getT() instanceof Movie)
                    .map(e -> (Movie) e.getT())
                    .anyMatch(k -> k.getId().matches(id))) {
                return true;
            }else  if (list.stream()
                    .filter(in -> in.getT() instanceof Cartoon)
                    .map(e -> (Cartoon) e.getT())
                    .anyMatch(k -> k.getId().matches(id))) {
                return true;
            }else  if (list.stream()
                    .filter(in -> in.getT() instanceof Series)
                    .map(e -> (Series) e.getT())
                    .anyMatch(k -> k.getId().matches(id))) {
                return true;
            }
            return false;
    }

    private User payProccess(User user, int price) {

        int money;
        if (user.getMoney() < price) {
            log.info("You dont have enough money to buy this product");
            throw new BadRequestException("User does not have any money");
        }

        money = user.getMoney() - price;

        user.setMoney(money);
        return user;
    }

    public void buyContent(Object object, String id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = this.findUserByUsername(authentication.getPrincipal().toString());

        if (user.get().getOwnedItems() == null) {
            throw new NotFoundException("List is null");
        }

        if (object instanceof Movie) {
               if (ifUserBoughtContent(user.get().getOwnedItems(), id)) {
                    log.info("User has this item");
                    return;
                }
                var movie =  movieRepository.findById(id);
                user.get().getOwnedItems().add(new BoughtContent("Movie", movie.get()));

                //

                this.userRepository.save(payProccess(user.get(), movie.get().getPrice()));
                log.info("Bought movie");

        }else if (object instanceof Series) {
            if (ifUserBoughtContent(user.get().getOwnedItems(), id)) {
                log.info("User has that item");
                return;
            }
            var series = seriesRepository.findById(id);
            //user.get().getOwnedItems().add(series.get());
            user.get().getOwnedItems().add(new BoughtContent("Series", series.get()));
            log.info("Bought series");
            this.userRepository.save(payProccess(user.get(), series.get().getPrice()));
        }else {
            throw new NotFoundException("Class type cannot be found !");
        }

    }

    public String getIndexPage(Model model, MovieService movieService, SeriesService seriesService) {
        //check koji user je loged in trenutno
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserController.displayName = authentication.getPrincipal().toString();

        if (UserController.displayName.matches("anonymousUser")) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else if (UserController.displayName.matches("Admin")) {
            // return "redirect:/api/v1/videoteka/admin-add-delete/movies";
        }

        model.addAttribute("username", authentication.getPrincipal().toString());
        if(UserController.displayName != null && !UserController.displayName.matches("anonymousUser")) {
            model.addAttribute("user", getUserProfile());
        }
        Map<String, Object> temp = new HashMap<>();
        temp.put(Types.movieType, movieService.getAllContent().stream().filter(rating -> rating.getRating() > 8.5).collect(Collectors.toList()));
        temp.put(Types.seriesType, seriesService.getAllContent().stream().filter(rating -> rating.getRating() > 8.5).collect(Collectors.toList()));
        model.addAttribute("temp", temp);

        return "videoteka/index.html";
    }

    public String submitLogin(User user, Model model, String getIndex) {
        try {
            if(login(user.getUsername(), user.getPassword()) != null) {
                if (getIndex != null) {
                    return "redirect:/api/v1/videoteka/index";
                }
            }
        }catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "videoteka/errorHandling.html";
        }

        model.addAttribute("errorMessage", "unknown error occurred");
        return "videoteka/errorHandling.html";
    }

}
