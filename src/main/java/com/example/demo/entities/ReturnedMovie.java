package com.example.demo.entities;

import com.example.demo.entities.compositeKeys.ReturnId;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "ReturnedMovie")
@Table(name = "ReturnedMovie")
public class ReturnedMovie {

    @EmbeddedId
    private ReturnId returnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("socialSecurityNumber")
    private Customer customer;

    @Column(name = "returnDate")
    private java.util.Date returnDate;

    public ReturnedMovie(){}

    public ReturnedMovie(Movie movie, Customer customer, java.util.Date returnDate){
        this.movie=movie;
        this.customer=customer;
        this.returnId =new ReturnId(movie.getMovieId(), customer.getSocialSecurityNumber());
        this.returnDate=returnDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ReturnId getReturnId() {
        return returnId;
    }

    public void setReturnId(ReturnId returnId) {
        this.returnId = returnId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public java.util.Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(java.util.Date returnDate) {
        this.returnDate = returnDate;
    }

}

