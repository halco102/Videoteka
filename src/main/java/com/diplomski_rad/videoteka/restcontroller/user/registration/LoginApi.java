package com.diplomski_rad.videoteka.restcontroller.user.registration;

import com.diplomski_rad.videoteka.payload.request.SigninRequest;
import com.diplomski_rad.videoteka.restcontroller.service.UserRegistration;
import com.diplomski_rad.videoteka.service.persons.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginApi {

    @Autowired
    UserRegistration userService;

    @PostMapping("")
    public ResponseEntity<?> loginUserToApp (@RequestBody SigninRequest signinRequest) {
        return new ResponseEntity<>(this.userService.signinResponse(signinRequest), HttpStatus.OK);
    }

}
