package com.example.demo.formModels;

import java.util.Date;

public class RentalIdForm {

    private String socialSecurityNumber;
    private Long movieId;
    private Date rentalDate;


    public RentalIdForm(){}

    public RentalIdForm(String socialSecurityNumber, Long movieId, Date rentalDate){
        this.socialSecurityNumber=socialSecurityNumber;
        this.movieId=movieId;
        this.rentalDate=rentalDate;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }
}
