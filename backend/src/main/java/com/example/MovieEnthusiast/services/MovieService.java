package com.example.MovieEnthusiast.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.MovieEnthusiast.model.Movie;
import com.example.MovieEnthusiast.model.SimpleMovie;
import com.example.MovieEnthusiast.model.MovieApiResponse;
import com.example.MovieEnthusiast.model.SimpleMovieApiResponse;

@Service
public class MovieService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public MovieService(RestTemplate restTemplate, @Value("${api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<SimpleMovie> fetchSimpleMovies(int page) {
        String url = "https://api.themoviedb.org/3/movie/popular";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey)
                .queryParam("page", page);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleMovieApiResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                SimpleMovieApiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<SimpleMovie> movies = Objects.requireNonNull(response.getBody()).getResults();
            return movies.stream().map(this::fetchMovieRevenue)
                    .peek(movie -> movie.setReleaseDate(getReleaseYear(movie.getReleaseDate())))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Failed to fetch movies: " + response.getStatusCode());
        }
    }

    private SimpleMovie fetchMovieRevenue(SimpleMovie movie) {
        String url = "https://api.themoviedb.org/3/movie/" + movie.getId();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleMovie> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                SimpleMovie.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            SimpleMovie detailedMovie = response.getBody();
            movie.setRevenue(detailedMovie.getRevenue());
            return movie;
        } else {
            throw new RuntimeException("Failed to fetch movie details: " + response.getStatusCode());
        }
    }

    public Movie fetchMovieDetails(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode movieJson = objectMapper.readTree(response.getBody());

                List<String> genres = new ArrayList<>();
                JsonNode genresArray = movieJson.get("genres");
                for (JsonNode genreNode : genresArray) {
                    genres.add(genreNode.get("name").asText());
                }

                Movie movie = new Movie();
                movie.setId(movieId);
                movie.setGenre(genres);

                movie.setTitle(movieJson.get("title").asText());
                movie.setReleaseDate(movieJson.get("release_date").asText());
                movie.setRevenue(movieJson.get("revenue").asText());
                movie.setRuntime(movieJson.get("runtime").asText());
                movie.setVote_average(movieJson.get("vote_average").asText());
                movie.setVote_count(movieJson.get("vote_count").asText());
                movie.setOverview(movieJson.get("overview").asText());
                ArrayList<ArrayList<String>> movieTeam = fetchMovieCrew(movieId);
                movie.setActor(movieTeam.get(0));
                movie.setDirector(movieTeam.get(1));
                return movie;
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse movie details", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch movie details: " + response.getStatusCode());
        }
    }

    private ArrayList<ArrayList<String>> fetchMovieCrew(Long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode movieJson = objectMapper.readTree(response.getBody());

                ArrayList<String> director = new ArrayList<>();
                JsonNode crewArray = movieJson.get("crew");
                for (JsonNode crewNode : crewArray) {
                    if (crewNode.get("job").asText().equals("Director")) {
                        director.add(crewNode.get("name").asText());
                    }
                }

                ArrayList<String> actors = new ArrayList<>();
                JsonNode castArray = movieJson.get("cast");
                for (JsonNode castNode : castArray) {
                    actors.add(castNode.get("original_name").asText());
                    if (actors.size() > 4)
                        break;
                }

                ArrayList<ArrayList<String>> movieTeam = new ArrayList<>();
                movieTeam.add(actors);
                movieTeam.add(director);

                return movieTeam;
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse movie details", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch movie details: " + response.getStatusCode());
        }
    }

    public List<SimpleMovie> fetchTopRevenueMovies() {
        String url = "https://api.themoviedb.org/3/discover/movie?sort_by=revenue.desc&page=1";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleMovieApiResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                SimpleMovieApiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<SimpleMovie> movies = Objects.requireNonNull(response.getBody()).getResults();
            return movies.stream().map(this::fetchMovieRevenue)
                    .peek(movie -> movie.setReleaseDate(getReleaseYear(movie.getReleaseDate())))
                    .limit(10)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Failed to fetch movies: " + response.getStatusCode());
        }
    }

    public List<SimpleMovie> fetchTopRevenueMoviesPerYear(Long year) {
        String url = "https://api.themoviedb.org/3/discover/movie?sort_by=revenue.desc&primary_release_year=" + year
                + "&page=1";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<SimpleMovieApiResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                SimpleMovieApiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<SimpleMovie> movies = Objects.requireNonNull(response.getBody()).getResults();
            return movies.stream().map(this::fetchMovieRevenue)
                    .peek(movie -> movie.setReleaseDate(getReleaseYear(movie.getReleaseDate())))
                    .limit(10)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Failed to fetch movies: " + response.getStatusCode());
        }
    }

    private String getReleaseYear(String releaseDate) {
        if (releaseDate != null) {
            LocalDate date = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
            return String.valueOf(date.getYear());
        } else {
            return "NA";
        }
    }
}
