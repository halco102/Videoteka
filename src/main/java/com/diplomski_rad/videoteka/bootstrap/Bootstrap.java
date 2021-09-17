package com.diplomski_rad.videoteka.bootstrap;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    FusionAuth fusionAuth;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final SeriesRepository seriesRepository;
    private final CartoonRepository cartoonRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StarRepository starRepository;
    private final CreatorRepository creatorRepository;
    private final CountryRepository countryRepository;

    public Bootstrap(MovieRepository movieRepository,
                     GenreRepository genreRepository,
                     SeriesRepository seriesRepository,
                     CartoonRepository cartoonRepository,
                     UserRepository userRepository,
                     RoleRepository roleRepository,
                     StarRepository starRepository,
                     CreatorRepository creatorRepository,
                     CountryRepository countryRepository){
        this.movieRepository=movieRepository;
        this.genreRepository=genreRepository;
        this.seriesRepository=seriesRepository;
        this.cartoonRepository=cartoonRepository;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.starRepository=starRepository;
        this.creatorRepository=creatorRepository;
        this.countryRepository=countryRepository;
    }

    @Override
    public void run(String... args) throws Exception {


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
/*
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
*/
/*
        user.getRoles().add(role1);
        user2.getRoles().add(role1);
        user1.getRoles().add(role);*//*


        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
*/

        //ennd

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


        //create movie
        Movie movie = new Movie("Wolf of the Wall Street",1999,20);
        Movie movie1 = new Movie("Test2",2002,40);
        Movie movie2 = new Movie("KKK",2003,30);
        Movie movie3 = new Movie("Test3",2004,50);

        movie.getStars().addAll(Arrays.asList(star,star2,star1,star3));
        movie1.getStars().addAll(Arrays.asList(star3,star2));
        movie2.getStars().addAll(Arrays.asList(star1,star2));
        movie3.getStars().addAll(Arrays.asList(star,star1));
        //save movie
        movieRepository.save(movie);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        Series series = new Series("Test1",1999,20);
        Series series1 = new Series("Test2",2002,40);
        Series series2 = new Series("Test3",2001,21);
        Series series3 = new Series("KKK",2010,10);

        series.getStars().addAll(Arrays.asList(star,star2,star1,star3));
        series1.getStars().addAll(Arrays.asList(star3,star2));
        series2.getStars().addAll(Arrays.asList(star1,star2));
        series3.getStars().addAll(Arrays.asList(star,star1));

        seriesRepository.save(series);
        seriesRepository.save(series1);
        seriesRepository.save(series2);
        seriesRepository.save(series3);

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



        Cartoon cartoon = new Cartoon("Test1",1999,20);
        Cartoon cartoon1 = new Cartoon("Test2",2002,10);
        Cartoon cartoon2 = new Cartoon("Test3",2007,4);
        Cartoon cartoon3 = new Cartoon("KKK",2011,7);

        cartoon.getCreators().addAll(Arrays.asList(creator,creator1));
        cartoon1.getCreators().addAll(Arrays.asList(creator1,creator2));
        cartoon2.getCreators().addAll(Arrays.asList(creator3,creator1));
        cartoon3.getCreators().addAll(Arrays.asList(creator2));


        cartoonRepository.save(cartoon);
        cartoonRepository.save(cartoon1);
        cartoonRepository.save(cartoon2);
        cartoonRepository.save(cartoon3);
        //create genres
        Genre g1 = new Genre("g1");
        Genre g2 = new Genre("g2");
        Genre g3 = new Genre("g3");
        Genre g4 = new Genre("g4");
        Genre g5 = new Genre("g5");
        Genre g6 = new Genre("g6");
        Genre g7 = new Genre("g7");
        Genre g8 = new Genre("g8");
        Genre g9 = new Genre("g9");
        Genre g10 = new Genre("g10");
        Genre g11 = new Genre("g11");
        Genre g12= new Genre("g12");

        //save genres
        genreRepository.saveAll(Arrays.asList(g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,g11,g12));

        //add genres to movie
        movie.getGenres().addAll(Arrays.asList(g1,g2,g3));
        movie1.getGenres().addAll(Arrays.asList(g1,g3));
        movie2.getGenres().addAll(Arrays.asList(g1));
        movie3.getGenres().addAll(Arrays.asList(g5));
        //update movie
        movieRepository.save(movie);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);

        series.getGenres().addAll(Arrays.asList(g1,g2,g3));
        series1.getGenres().addAll(Arrays.asList(g1,g3));
        series2.getGenres().addAll(Arrays.asList(g1));
        series3.getGenres().addAll(Arrays.asList(g5));


        seriesRepository.save(series);
        seriesRepository.save(series1);
        seriesRepository.save(series2);
        seriesRepository.save(series3);

        cartoon.getGenres().addAll(Arrays.asList(g1,g2,g3));
        cartoon1.getGenres().addAll(Arrays.asList(g1,g3));
        cartoon2.getGenres().addAll(Arrays.asList(g1));
        cartoon3.getGenres().addAll(Arrays.asList(g5,g6));

        cartoonRepository.save(cartoon);
        cartoonRepository.save(cartoon1);
        cartoonRepository.save(cartoon2);
        cartoonRepository.save(cartoon3);


        System.out.println("Bootsrap ended!");


    }
}
