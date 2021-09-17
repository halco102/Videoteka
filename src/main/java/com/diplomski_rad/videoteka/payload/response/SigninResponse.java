package com.diplomski_rad.videoteka.payload.response;

import com.diplomski_rad.videoteka.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponse {

    private String id;

    private String token;

    private User user;

}