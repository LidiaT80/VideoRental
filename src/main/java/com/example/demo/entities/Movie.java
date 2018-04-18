package com.example.demo.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Movie")
@Table(name = "Movie")
@DynamicUpdate
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "movieId")
    private long movieId;
    private String name;
    private String description;
    private String releaseDate;
    private String category;
    private String movieFormat;
    private boolean available;

    @OneToMany(mappedBy = "movie",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    private List<Rental> customers= new ArrayList<>();



    public Movie(){
        available=true;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

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

    public boolean getAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available=available;
    }

    public List<Rental> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Rental> customers) {
        this.customers = customers;
    }
}
