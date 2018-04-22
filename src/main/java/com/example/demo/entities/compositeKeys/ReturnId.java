package com.example.demo.entities.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(getMovieId(), getSocialSecurityNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnId)) return false;
        ReturnId that = (ReturnId) o;
        return Objects.equals(getMovieId(), that.getMovieId()) &&
                Objects.equals(getSocialSecurityNumber(), that.getSocialSecurityNumber());
    }
}