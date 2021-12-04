package com.diplomski_rad.videoteka.restcontroller.service;

import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SigninRequest;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.payload.response.SigninResponse;
import com.diplomski_rad.videoteka.repository.person.AbstractPersonRepository;
import com.diplomski_rad.videoteka.repository.person.UserRepository;
import com.diplomski_rad.videoteka.service.persons.AbstractPersonService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistration extends AbstractPersonService<User> {

    private final String appId = "c155033d-8a15-4bea-ad87-b57bc4d65d64";

    @Autowired
    FusionAuth fusionAuth;

    public UserRegistration(AbstractPersonRepository<User> abstractPersonRepository) {
        super(abstractPersonRepository);
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

    public SigninResponse signinResponse (SigninRequest signinRequest) {
        SigninRequest signupRequest = new SigninRequest(appId, signinRequest.getEmail(), signinRequest.getPassword());
        SigninResponse response = null;
        try {
            response = fusionAuth.signIn(signupRequest);
        }catch (FeignException.FeignClientException e) {
            throw new FeignException.NotFound(e.getMessage(), e.request(), e.content());
        }

        return response;
    }

}
