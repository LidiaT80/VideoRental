package com.example.demo.formModels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MovieDataForm {

    @NotNull
    @Size(min = 1, max = 30)
    private String name;
    @NotNull
    @Size(min = 5, max = 250)
    private String description;
    @NotNull
    private String releaseDate;
    @NotNull
    @Size(min = 3, max = 15)
    private String category;
    @NotNull
    @Size(min = 3, max = 15)
    private String movieFormat;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMovieFormat() {
        return movieFormat;
    }

    public void setMovieFormat(String movieFormat) {
        this.movieFormat = movieFormat;
    }



}
