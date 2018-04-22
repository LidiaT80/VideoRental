package com.example.demo.formModels;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDataForm {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 30)
    private String address;
    @NotNull
    @NotEmpty
    @Size(min = 5,max = 6)
    private String postalCode;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    private String city;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    private String country;
    @NotNull
    @NotEmpty
    @Size(min = 9, max = 12)
    private String phone;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 13)
    private String socialSecurityNumber;


    public String getName() {
        return name;
    }

    public CustomerDataForm setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerDataForm setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CustomerDataForm setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CustomerDataForm setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CustomerDataForm setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerDataForm setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerDataForm setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public CustomerDataForm setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
        return this;
    }
}
