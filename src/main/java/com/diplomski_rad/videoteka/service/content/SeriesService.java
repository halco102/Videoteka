package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.constants.Types;
import com.diplomski_rad.videoteka.controller.person.UserController;
import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.repository.content.SeriesRepository;
import com.diplomski_rad.videoteka.service.GenreService;
import com.diplomski_rad.videoteka.service.persons.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SeriesService extends AbstractContentService<Series> {

    @Autowired
    SeriesRepository seriesRepository;

    @Autowired
    UserService userService;

    @Autowired
    GenreService genreService;

    public SeriesService(SeriesRepository seriesRepository) {
        super(seriesRepository);
    }

    @Override
    public List<Series> getAllContent() {
        return super.getAllContent();
    }

    @Override
    public Optional<Series> getContentById(String id) {
        return super.getContentById(id);
    }

    @Override
    public Series saveContent(Series entitiy) {
        return super.saveContent(entitiy);
    }

    @Override
    public Series updateById(String id, Series entity) {
        return super.updateById(id, entity);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public Series getByName(String name) {
        return super.getByName(name);
    }

    @Override
    public List<Series> findByKeyword(String keyword) {
        return super.findByKeyword(keyword);
    }

    @Override
    public List<Series> searchEngine(String keyword) {
        return super.searchEngine(keyword);
    }

    private String submitAdminForm(Series series, List<Genre> genres) {

        if (series.getId() != null) {
            var oldSeries = seriesRepository.findById(series.getId()).get();

            if (genres != null) {
                series.getGenres().clear();
                series.getGenres().addAll(genres);
            }else {
                series.getGenres().addAll(oldSeries.getGenres());
            }

            seriesRepository.save(series);
            return "redirect:/api/v1/videoteka/series";
        }
        seriesRepository.save(series);
        return "redirect:/api/v1/videoteka/admin/series";
    }


    public String getAllSeries(Model model, String keyword) {
        model.addAttribute("contents", seriesRepository.findAll());
        model.addAttribute("username", UserController.displayName);
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("links", getType(Types.seriesType));
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
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("links", getType(Types.seriesType));
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

    public String getSeriesById(Model model, String id) {
        var content = getContentById(id).orElse(null);
        model.addAttribute("content", content);
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("username", UserController.displayName);

        if(UserController.displayName != null && !UserController.displayName.matches("anonymousUser")) {
            var user = userService.getUserProfile();
            model.addAttribute("role", user.getRoles().first());
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
        model.addAttribute("title", Types.seriesType);
        model.addAttribute("genres", genreService.findAllGenres());
        return "videoteka/admin/admin.html";
    }


    public String postAdminForm(Series movies,
                                BindingResult bindingResult,
                                List<Genre> genres,
                                Model model){

        if(bindingResult.hasErrors()) {
            model.addAttribute("title", Types.seriesType);
            model.addAttribute("genres", genreService.findAllGenres());
            return "videoteka/admin/admin.html";
        }
        return submitAdminForm(movies, genres);
    }


    public String getSeriesForm(Model model, String id) {
        var series = getContentById(id);
        model.addAttribute("content", series.get());
        model.addAttribute("genres", genreService.findAllGenres());
        model.addAttribute("title", Types.seriesType);
        return "videoteka/admin/admin.html";
    }

}
