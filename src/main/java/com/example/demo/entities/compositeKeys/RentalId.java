package com.example.demo.entities.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Embeddable
public class RentalId implements Serializable{

    @Column(name = "movieId")
    private Long movieId;

    @Column(name = "socialSecurityNumber")
    private String socialSecurityNumber;

    @Column(name = "rentalDate")
    private java.util.Date rentalDate;

    public RentalId() {}

    public RentalId(Long movieId, String socialSecurityNumber, java.util.Date rentalDate) {
        this.movieId=movieId;
        this.socialSecurityNumber=socialSecurityNumber;
        this.rentalDate=rentalDate;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public java.util.Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(java.util.Date rentalDate) {
        this.rentalDate = rentalDate;
    }


}
