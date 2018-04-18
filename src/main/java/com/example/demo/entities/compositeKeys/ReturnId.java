package com.example.demo.entities.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ReturnId implements Serializable{

    @Column(name = "movieId")
    private Long movieId;

    @Column(name = "socialSecurityNumber")
    private String socialSecurityNumber;

    public ReturnId() {}

    public ReturnId(Long movieId, String socialSecurityNumber) {
        this.movieId=movieId;
        this.socialSecurityNumber=socialSecurityNumber;
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
}