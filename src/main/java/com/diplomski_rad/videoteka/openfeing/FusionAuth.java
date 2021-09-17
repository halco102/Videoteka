package com.diplomski_rad.videoteka.openfeing;

import com.diplomski_rad.videoteka.model.User;
import com.diplomski_rad.videoteka.payload.request.SigninRequest;
import com.diplomski_rad.videoteka.payload.request.SignupRequest;
import com.diplomski_rad.videoteka.payload.response.SigninResponse;
import com.diplomski_rad.videoteka.payload.response.SignupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.SortedSet;

@FeignClient(value = "fusionauth", url = "${FUSIONAUTH_URL}")
public interface FusionAuth {

    @PostMapping(value = "/api/v1/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SigninResponse signIn (@RequestBody SigninRequest signinRequest);

    @PostMapping(value = "/api/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SignupResponse signUp (@RequestBody SignupRequest signupRequest);

    @PostMapping(value = "/api/v1/auth/admin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SignupResponse createAdmin (@RequestBody SignupRequest signupRequest);

    @GetMapping(value = "/api/v1/auth/user/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    User getUserByUsername(@PathVariable String username);

    @GetMapping(value = "/api/v1/auth/user/roles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SortedSet<String> getUserRolesFromApp(@RequestParam String appId, @RequestParam String userId);

    @GetMapping(value = "/api/v1/auth/jwt/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    boolean validateJwt(@RequestParam String jwt);

}
