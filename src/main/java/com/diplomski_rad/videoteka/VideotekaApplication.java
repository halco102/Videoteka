package com.diplomski_rad.videoteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VideotekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideotekaApplication.class, args);
	}

}
