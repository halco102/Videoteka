/*
package com.diplomski_rad.videoteka.bootstrap;

import com.diplomski_rad.videoteka.externalapi.feign.ContentApi;
import com.diplomski_rad.videoteka.externalapi.model.GenreResult;
import com.diplomski_rad.videoteka.externalapi.model.MainModel;
import com.diplomski_rad.videoteka.model.*;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.repository.GenreRepository;
import com.diplomski_rad.videoteka.repository.RoleRepository;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import com.diplomski_rad.videoteka.repository.content.SeriesRepository;
import com.diplomski_rad.videoteka.repository.person.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    @Autowired
    FusionAuth fusionAuth;

    private final String avatarUrl = "https://avatars.dicebear.com/api/bottts/";

    @Value("${apiKey}")
    private String apiKey;

    @Value("${fusionauth.appId}")
    private String fusionauthAppId;

    private Set<Genre> myGenres = new HashSet<>();

    private Set<Genre> currentContentGenre;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ContentApi contentApi;

    public Bootstrap(MovieRepository movieRepository,
                     GenreRepository genreRepository,
                     SeriesRepository seriesRepository,
                     UserRepository userRepository,
                     RoleRepository roleRepository,
                     ContentApi contentApi){
        this.movieRepository=movieRepository;
        this.genreRepository=genreRepository;
        this.seriesRepository=seriesRepository;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.contentApi = contentApi;
    }

    private int randomPriceGenerator() {
        Random random = new Random();
        int price = random.nextInt(50);
        return price;
    }

    private List<String> listOfElements(Resource resource) throws IOException {
        List<String> ids = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(resource.getInputStream())
        );
        String id;
        while ((id = bufferedReader.readLine()) != null) {
            ids.add(id);
        }

        return ids;
    }

    @Override
    public void run(String... args) throws Exception {

        Resource movieResource = new ClassPathResource("/contentids/MovieIds.txt");
        Resource seriesResource = new ClassPathResource("/contentids/SeriesIds.txt");

        System.out.println("Bootstrap started!");
        Role role = new Role("ADMIN");
        Role role1 = new Role("USER");
        roleRepository.save(role);
        roleRepository.save(role1);

        User user1 = new User("Admin","Admin","Admin","password123","password123","admin@hotmail.com");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(user1.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        signupRequest.setPassword(user1.getPassword());
        signupRequest.setEmail(user1.getEMail());
        signupRequest.setAppId(fusionauthAppId);

        if (userRepository.checkIfUserExists("Admin").isEmpty()) {
            var fa = fusionAuth.createAdmin(signupRequest);
            User temp = new User();
            temp.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
            temp.setEMail(fa.getEmail());
            temp.setUsername(fa.getUsername());
            SortedSet<String> t = new TreeSet<>();
            t.add("ROLE_ADMIN");
            temp.setRoles(t);

            userRepository.save(temp);
        }

        //add all genres
        GenreResult allGenres = this.contentApi.getAllGenres(apiKey);
        allGenres.getResults().stream().forEach(g -> genreRepository.save(new Genre(null, g.getGenre())));

        List<Movie> movies = new ArrayList<>();
        listOfElements(movieResource).forEach(item -> {
            log.info(item);
            MainModel model = contentApi.getMovieByImdbId(apiKey, item);
            Movie movie = new Movie(model.getData().getTitle(),
                    model.getData().getRelease(),
                    model.getData().getMovie_length(),
                    model.getData().getImage_url(),
                    model.getData().getDescription(),
                    model.getData().getTrailer(),
                    model.getData().getRating(),
                    model.getData().getGen(),
                    model.getData().getContent_rating());
                    currentContentGenre = new HashSet<>();
            for (Genre ge : model.getData().getGen()
                    ) {
                currentContentGenre.add(genreRepository.findGenreByName(ge.getName()).get());
            }
            //so that we dont have duplicates
            movie.setGenres(currentContentGenre);
            movie.setPrice(randomPriceGenerator());
            movies.add(movie);

        });

        for (Movie m: movies
             ) {
            movieRepository.save(m);
            log.info("Saved movie");
        }

        List<Series> series = new ArrayList<>();
        listOfElements(seriesResource).forEach(item -> {
            MainModel model = contentApi.getSeriesByImdbId(apiKey, item);
            Series series1 = new Series(model.getData().getTitle(),
                    model.getData().getRelease(),
                    model.getData().getImage_url(),
                    model.getData().getDescription(),
                    model.getData().getTrailer(),
                    model.getData().getRating(),
                    model.getData().getGen(),
                    model.getData().getMovie_length(),
                    model.getData().getContent_rating());
                    currentContentGenre = new HashSet<>();
            for (Genre ge : model.getData().getGen()
            ) {
                currentContentGenre.add(genreRepository.findGenreByName(ge.getName()).get());
            }
            series1.setGenres(currentContentGenre);
            series1.setPrice(randomPriceGenerator());
            series.add(series1);

        });


        for (Series s: series
        ) {
            seriesRepository.save(s);
            log.info("Saved series");
        }

        System.out.println("Bootsrap ended!");

    }
}
*/
