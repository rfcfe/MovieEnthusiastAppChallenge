package com.example.MovieEnthusiast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.MovieEnthusiast.services.MovieService;
import com.example.MovieEnthusiast.model.Movie;
import com.example.MovieEnthusiast.model.SimpleMovie;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies(@RequestParam(defaultValue = "1") int page) {
        List<SimpleMovie> movieList = movieService.fetchSimpleMovies(page);
        if (movieList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find movies at page " + page);
        }
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/movieDetails/{id}")
    public ResponseEntity<?> getMovieDetails(@PathVariable long id) {
        Movie movieDetails = movieService.fetchMovieDetails(id);
        if (movieDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie details not found");
        }
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/topRevenue")
    public ResponseEntity<?> getTopRevenueMovies() {
        List<SimpleMovie> topRevenueMovies = movieService.fetchTopRevenueMovies();
        if (topRevenueMovies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Top revenue movies not found");
        }
        return ResponseEntity.ok(topRevenueMovies);
    }

    @GetMapping("/topRevenue/{year}")
    public ResponseEntity<?> getTopRevenueMoviesPerYear(@PathVariable long year) {
        List<SimpleMovie> movieDetails = movieService.fetchTopRevenueMoviesPerYear(year);
        if (movieDetails == null || movieDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Top revenue movies of the year " + year + " not found");
        }
        return ResponseEntity.ok(movieDetails);
    }

}
