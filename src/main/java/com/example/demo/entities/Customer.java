package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private String email;
    @Id
    private String socialSecurityNumber;

    public Customer(){}

    public Customer(String name, String address, String postalCode, String city, String country, String phone,
                    String email, String socialSecurityNumber){
        this.name=name;
        this.address=address;
        this.postalCode=postalCode;
        this.city=city;
        this.country=country;
        this.phone=phone;
        this.email=email;
        this.socialSecurityNumber=socialSecurityNumber;
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

}
