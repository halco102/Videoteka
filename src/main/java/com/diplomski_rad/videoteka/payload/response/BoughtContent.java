package com.diplomski_rad.videoteka.payload.response;

import com.diplomski_rad.videoteka.model.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoughtContent extends Content {

    private String className;

    private Object t;

}
