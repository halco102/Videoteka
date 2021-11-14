package com.diplomski_rad.videoteka.controller.person;

import com.diplomski_rad.videoteka.model.Movie;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.openfeing.FusionAuth;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    FusionAuth fusionAuth;

    @PostMapping("")
    public ResponseEntity<?> createAdmin(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(fusionAuth.createAdmin(signupRequest), HttpStatus.OK);
    }

}
