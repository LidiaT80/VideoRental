package com.example.demo.controllers;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Movie;
import com.example.demo.entities.Rental;
import com.example.demo.entities.compositeKeys.RentalId;
import com.example.demo.formModels.RentalIdForm;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("rentalPage")
    public ModelAndView rentalPage(){
        return new ModelAndView("rental_management/RentalPage");
    }

    @RequestMapping("renting")
    public ModelAndView rentAMovie(Model model){
        model.addAttribute("rentalIdForm",new RentalIdForm());
        return new ModelAndView("rental_management/RentAMovie").addObject("rentalIdForm");
    }

    @RequestMapping("rent")
    public ModelAndView rent(@ModelAttribute RentalIdForm rentalIdForm){
        RentalId rentalId=new RentalId();
        rentalId.setMovieId(rentalIdForm.getMovieId());
        rentalId.setSocialSecurityNumber(rentalIdForm.getSocialSecurityNumber());
        rentalId.setRentalDate(rentalIdForm.getRentalDate());

        Movie movie=movieRepository.findById(rentalIdForm.getMovieId()).get();
        movie.setAvailable(false);
        Customer customer=customerRepository.findById(rentalIdForm.getSocialSecurityNumber()).get();
        Rental rental= new Rental(movie, customer, rentalId);

        rentalRepository.save(rental);
        return new ModelAndView("rental_management/RentAgain").addObject("rentalId",rentalId);

    }
}
