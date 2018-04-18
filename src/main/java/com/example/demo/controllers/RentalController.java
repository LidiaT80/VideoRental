package com.example.demo.controllers;

import com.example.demo.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;
}
