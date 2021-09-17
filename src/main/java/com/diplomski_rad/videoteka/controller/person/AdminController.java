package com.diplomski_rad.videoteka.controller.person;

import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    FusionAuth fusionAuth;

    @PostMapping("")
    public ResponseEntity<?> createAdmin(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(fusionAuth.createAdmin(signupRequest), HttpStatus.OK);
    }

}
