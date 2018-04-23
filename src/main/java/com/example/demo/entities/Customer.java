package com.example.demo.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Customer")
@Table(name = "Customer")
@DynamicUpdate
public class Customer {

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private String email;
    @Id
    @Column(name = "socialSecurityNumber")
    private String socialSecurityNumber;

    @OneToMany(mappedBy = "customer",
    cascade = CascadeType.ALL)
    private List<Rental> rentedMovies =new ArrayList<>();

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL)
    private List<ReturnedMovie> returnedMovies=new ArrayList<>();

    public Customer(){}

    public Customer(String socialSecurityNumber, String address, String city, String country,
                    String email, String name, String phone, String postalCode){
        this.name=name;
        this.address=address;
        this.postalCode = postalCode;
        this.city=city;
        this.country=country;
        this.phone=phone;
        this.email=email;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public List<Rental> getRentedMovies() {
        return rentedMovies;
    }

    public void setRentedMovies(List<Rental> movies) {
        this.rentedMovies = movies;
    }

    public List<ReturnedMovie> getReturnedMovies() {
        return returnedMovies;
    }

    public void setReturnedMovies(List<ReturnedMovie> returnedMovies) {
        this.returnedMovies = returnedMovies;
    }
}
