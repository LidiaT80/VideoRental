package com.example.demo.controllers;

import com.example.demo.repositories.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnController {

    @Autowired
    private ReturnRepository returnRepository;
}
