package com.diplomski_rad.videoteka.service.persons;

import com.diplomski_rad.videoteka.exception.NotFoundException;
import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SigninRequest;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.payload.response.SigninResponse;
import com.diplomski_rad.videoteka.payload.response.SignupResponse;
import com.diplomski_rad.videoteka.repository.person.UserRepository;
import feign.FeignException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends AbstractPersonService<User> {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final FusionAuth fusionAuth;


    public static String jwtLoggedUser;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FusionAuth fusionAuth) {
        super(userRepository);
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fusionAuth = fusionAuth;
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


}
