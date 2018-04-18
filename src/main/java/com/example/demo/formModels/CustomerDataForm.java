package com.example.demo.formModels;

public class CustomerDataForm {

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String phone;
    private String email;
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
