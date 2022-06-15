package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService extends AbstractContentService<Movie>{

    @Autowired
    GenreService genreService;

    @Autowired
    UserService userService;

    public MovieService(MovieRepository movieRepository) {
        super(movieRepository);
    }

    @Override
    public List<Movie> getAllContent() {
        return super.getAllContent();
    }

    @Override
    public Optional<Movie> getContentById(String id) {
        return super.getContentById(id);
    }

    @Override
    public Movie saveContent(Movie entitiy) {
        return super.saveContent(entitiy);
    }

    @Override
    public Movie updateById(String id, Movie entity) {

        return super.updateById(id, entity);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public Movie getByName(String name) {
        return super.getByName(name);
    }

    @Override
    public List<Movie> findByKeyword(String keyword) {
        return super.findByKeyword(keyword);
    }

    @Override
    public List<Movie> searchEngine(String keyword) {
        return super.searchEngine(keyword);
    }


    private String submitAdminForm(Movie movies, List<Genre> genres, BindingResult bindingResult) {

        if (movies.getId() != null) {
            var oldMovie = getContentById(movies.getId()).get();

            if (genres != null) {
                movies.getGenres().clear();
                movies.getGenres().addAll(genres);
            }else {
                movies.getGenres().addAll(oldMovie.getGenres());
            }

            saveContent(movies);
            return "redirect:/api/v1/videoteka/movies";
        }

        if (genres == null || genres.isEmpty()) {
            ObjectError objectError = new ObjectError("genres", "npe");
            bindingResult.addError(objectError);
            return "redirect:/api/v1/videoteka/admin/movies";
        }else {
            movies.setGenres(genres.stream().collect(Collectors.toSet()));

        }

        saveContent(movies);
        return "redirect:/api/v1/videoteka/admin/movies";
    }

    //movies
    public String getAllMovies(Model model){

        model.addAttribute("contents", getAllContent());
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Types.movieType);
        model.addAttribute("links", getType(Types.movieType));
        if(UserController.displayName != null && !UserController.displayName.matches("anonymousUser")) {
            //get currently logged user profile
            var user = userService.getUserProfile();
            model.addAttribute("user", user);
            model.addAttribute("avatar", user.getAvatar());
            model.addAttribute("money", user.getMoney());

        }

        return  "videoteka/entertainment/main/main.html";
    }

    public String search(String name, Model model) {
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Types.movieType);
        model.addAttribute("links", getType(Types.movieType));
        model.addAttribute("contents", searchEngine(name));
        if(UserController.displayName != null && !UserController.displayName.matches("anonymousUser")) {
            //get currently logged user profile
            var user = userService.getUserProfile();
            model.addAttribute("user", user);
            model.addAttribute("avatar", user.getAvatar());
            model.addAttribute("money", user.getMoney());

        }
        return  "videoteka/entertainment/main/main.html";
    }

    public String getMovieById(Model model, String id) {
        var content = getContentById(id).orElse(null);
        model.addAttribute("content", content);
        model.addAttribute("title", Types.movieType);
        model.addAttribute("username", UserController.displayName);

        if(UserController.displayName != null && !UserController.displayName.matches("anonymousUser")) {
            var user = userService.getUserProfile();
            AtomicReference<String> role = new AtomicReference<>("");

            user.getRoles().stream().anyMatch(e -> {
                if (e.matches("ROLE_USER")) {
                    role.set("ROLE_USER");
                    return true;
                }else {
                    role.set("ROLE_ADMIN");
                    return false;
                }
            });

            log.info("ROLE " + role.get());

            model.addAttribute("role", role.get());
            model.addAttribute("ownedItems", user.getOwnedItems());
            model.addAttribute("avatar", user.getAvatar());
            model.addAttribute("money", user.getMoney());

            if (user.getOwnedItems().stream().anyMatch(boughtContent -> boughtContent.getT().equals(content))) {
                log.info("This content is already bought");
                model.addAttribute("alreadyBought", true);
            } else {
                model.addAttribute("alreadyBought", false);
            }

        }
        model.addAttribute("genres", content.getGenres());
        return "videoteka/entertainment/single_page.html";
    }

    public String getAdminPage(Model model){
        model.addAttribute("content", new Movie());
        model.addAttribute("title", Types.movieType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/admin.html";
    }

    public String postAdminForm(Movie movies,
                                  BindingResult bindingResult,
                                  List<Genre> genres,
                                  Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("title", Types.movieType);
            model.addAttribute("genres", genreService.findAllGenres());
            return "videoteka/admin/admin.html";
        }
        return submitAdminForm(movies, genres, bindingResult);
    }

    public String getMovieForm(Model model, String id) {
        var movie = getContentById(id);
        model.addAttribute("content", movie.get());
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Types.movieType);
        return "videoteka/admin/admin.html";
    }

}
