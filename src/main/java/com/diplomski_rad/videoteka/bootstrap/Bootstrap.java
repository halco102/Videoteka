/*
package com.diplomski_rad.videoteka.bootstrap;

import com.diplomski_rad.videoteka.externalapi.feign.ContentApi;
import com.diplomski_rad.videoteka.externalapi.model.MainModel;
import com.diplomski_rad.videoteka.model.*;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.repository.CountryRepository;
import com.diplomski_rad.videoteka.repository.GenreRepository;
import com.diplomski_rad.videoteka.repository.RoleRepository;
import com.diplomski_rad.videoteka.repository.content.CartoonRepository;
import com.diplomski_rad.videoteka.repository.content.MovieRepository;
import com.diplomski_rad.videoteka.repository.content.SeriesRepository;
import com.diplomski_rad.videoteka.repository.person.CreatorRepository;
import com.diplomski_rad.videoteka.repository.person.StarRepository;
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
import java.io.InputStreamReader;
import java.util.*;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

    @Autowired
    FusionAuth fusionAuth;

    @Value("${apiKey}")
    private String apiKey;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final SeriesRepository seriesRepository;
    private final CartoonRepository cartoonRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StarRepository starRepository;
    private final CreatorRepository creatorRepository;
    private final CountryRepository countryRepository;
    private final ContentApi contentApi;

    public Bootstrap(MovieRepository movieRepository,
                     GenreRepository genreRepository,
                     SeriesRepository seriesRepository,
                     CartoonRepository cartoonRepository,
                     UserRepository userRepository,
                     RoleRepository roleRepository,
                     StarRepository starRepository,
                     CreatorRepository creatorRepository,
                     CountryRepository countryRepository,
                     ContentApi contentApi){
        this.movieRepository=movieRepository;
        this.genreRepository=genreRepository;
        this.seriesRepository=seriesRepository;
        this.cartoonRepository=cartoonRepository;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.starRepository=starRepository;
        this.creatorRepository=creatorRepository;
        this.countryRepository=countryRepository;
        this.contentApi = contentApi;
    }

    private int randomPriceGenerator() {
        Random random = new Random();
        int price = random.nextInt(50);
        return price;
    }


    @Override
    public void run(String... args) throws Exception {
        */
/*List<String> imdbIds = new ArrayList<>(Arrays.asList("tt10942302", "tt0111161", "tt0068646", "tt5773402",));*//*


        List<String> imdbIds = new ArrayList<>();
        Resource resource = new ClassPathResource("/contentids/MovieIds.txt");
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(resource.getInputStream())
        );
        String id;
        while ((id = bufferedReader.readLine()) != null) {
            imdbIds.add(id);
        }

        System.out.println("Bootstrap started!");
        Role role = new Role("ADMIN");
        Role role1 = new Role("USER");
        roleRepository.save(role);
        roleRepository.save(role1);


        Country country = new Country("Bosna");
        Country country1 = new Country("Njemacka");
        Country country2 = new Country("Srbija");
        Country country3 = new Country("Slovenija");


        countryRepository.save(country);
        countryRepository.save(country1);
        countryRepository.save(country2);
        countryRepository.save(country3);

        //create user
        User user = new User("Admir","Halilovic","halco","123","admir@hotmail.com");
        User user1 = new User("Admin","Admin","Admin","password123","admin@hotmail.com");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(user1.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        signupRequest.setPassword(user1.getPassword());
        signupRequest.setEmail(user1.getEMail());
        signupRequest.setAppId("c155033d-8a15-4bea-ad87-b57bc4d65d64");

        if (userRepository.checkIfUserExists("Admin").isEmpty()) {
            var fa = fusionAuth.createAdmin(signupRequest);
            User temp = new User();
            temp.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
            temp.setEMail(fa.getEmail());
            temp.setUsername(fa.getUsername());

            userRepository.save(temp);
        }


        User user2 = new User("Lejla","Bandic","weejws","222","lejla@hotmail.com");


        //star
        Stars star = new Stars("Leonardo","DiCaprio");
        Stars star1 = new Stars("Christian","Bale");
        Stars star2 = new Stars("Brad","Pitt");
        Stars star3 = new Stars("Steve","Carell");

        starRepository.save(star);
        starRepository.save(star1);
        starRepository.save(star2);
        starRepository.save(star3);
        //end

        List<Movie> movies = new ArrayList<>();
        imdbIds.forEach(item -> {
            log.info(item);
            MainModel model = contentApi.getMoviesByPopularity(apiKey, item);
            Movie movie = new Movie(model.getData().getTitle(),
                    model.getData().getRelease(),
                    model.getData().getMovie_length(),
                    model.getData().getImage_url(),
                    model.getData().getDescription(),
                    model.getData().getTrailer(),
                    model.getData().getRating(),
                    model.getData().getGen());
            //add all genres to db
            Set<Genre> myGenres = new HashSet<>();
            for (Genre ge : model.getData().getGen()
                    ) {
               var temp = genreRepository.save(new Genre(null, ge.getName()));
               myGenres.add(temp);
            }
            movie.setGenres(myGenres);
            movie.setPrice(randomPriceGenerator());
            movies.add(movie);

        });


        for (Movie m: movies
             ) {
            movieRepository.save(m);
            log.info("Saved movie");
        }



        List<Series> series = new ArrayList<>();
        imdbIds.forEach(item -> {
            MainModel model = contentApi.getMoviesByPopularity(apiKey, item);
            Series series1 = new Series(model.getData().getTitle(),
                    model.getData().getRelease(),
                    model.getData().getImage_url(),
                    model.getData().getDescription(),
                    model.getData().getTrailer(),
                    model.getData().getRating(),
                    model.getData().getGen(),
                    model.getData().getMovie_length());
            //add all genres to db
            Set<Genre> myGenres = new HashSet<>();
            for (Genre ge : model.getData().getGen()
            ) {
                var temp = genreRepository.save(new Genre(null, ge.getName()));
                myGenres.add(temp);
            }
            series1.setGenres(myGenres);
            series1.setPrice(randomPriceGenerator());
            series.add(series1);

        });


        for (Series s: series
        ) {
            seriesRepository.save(s);
            log.info("Saved series");
        }



        Creator creator = new Creator("test1","test1");
        Creator creator1 = new Creator("test2","test2");
        Creator creator2 = new Creator("test3","test3");
        Creator creator3 = new Creator("test4","test4");

        creator.setCountry(country);
        creator1.setCountry(country1);
        creator2.setCountry(country2);

        creatorRepository.save(creator);
        creatorRepository.save(creator1);
        creatorRepository.save(creator2);
        creatorRepository.save(creator3);


        System.out.println("Bootsrap ended!");

    }
}
*/
