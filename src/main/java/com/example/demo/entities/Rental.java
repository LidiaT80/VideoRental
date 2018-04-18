package com.example.demo.entities;

import com.example.demo.entities.compositeKeys.RentalId;

import javax.persistence.*;


@Entity(name = "Rental")
@Table(name = "Rental")
public class Rental {

    @EmbeddedId
    private RentalId rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("socialSecurityNumber")
    private Customer customer;



    public Rental(){}

    public Rental(Movie movie, Customer customer){
        this.movie=movie;
        this.customer=customer;
        this.rentalId =new RentalId(movie.getMovieId(), customer.getSocialSecurityNumber(), rentalId.getRentalDate());
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public RentalId getRentalId() {
        return rentalId;
    }

    public void setRentalId(RentalId rentalId) {
        this.rentalId = rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
