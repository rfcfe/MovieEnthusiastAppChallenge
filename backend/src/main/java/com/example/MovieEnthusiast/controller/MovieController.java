package com.example.MovieEnthusiast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<SimpleMovie> getAllMovies(@RequestParam(defaultValue = "1") int page) {
        return movieService.fetchSimpleMovies(page);
    }

    @GetMapping("/movieDetails/{id}")
    public ResponseEntity<Movie> getMovieDetails(@PathVariable long id) {
        Movie movieDetails = movieService.fetchMovieDetails(id);
        return ResponseEntity.ok(movieDetails);
    }

    @GetMapping("/topRevenue")
    public List<SimpleMovie> getTopRevenueMovies() {
        return movieService.fetchTopRevenueMovies();
    }

    @GetMapping("/topRevenue/{year}")
    public ResponseEntity<List<SimpleMovie>> getTopRevenueMoviesPerYear(@PathVariable long year) {
        List<SimpleMovie> movieDetails = movieService.fetchTopRevenueMoviesPerYear(year);
        return ResponseEntity.ok(movieDetails);
    }

}
