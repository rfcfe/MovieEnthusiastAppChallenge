package com.example.MovieEnthusiast;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.MovieEnthusiast.config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class MovieEnthusiastWebappApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("API_KEY", dotenv.get("API_KEY"));
		SpringApplication.run(MovieEnthusiastWebappApplication.class, args);
	}
}