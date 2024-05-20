package com.example.MovieEnthusiast.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleMovie {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;
    
    @JsonProperty("revenue")
    private String revenue;

    @JsonProperty("release_date")
    private String releaseDate;

    public Long getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
