package com.example.MovieEnthusiast.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;
    @JsonProperty("revenue")
    private String revenue;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("runtime")
    private String runtime;

    @JsonProperty("genre")
    private List<String> genre;

    @JsonProperty("vote_average")
    private String vote_average;

    @JsonProperty("vote_count")
    private String vote_count;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("director")
    private List<String> director;

    @JsonProperty("actor")
    private List<String> actor;

    public Long getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }
}
