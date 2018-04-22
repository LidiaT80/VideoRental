package com.example.demo.controllers;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Movie;
import com.example.demo.entities.Rental;
import com.example.demo.entities.compositeKeys.RentalId;
import com.example.demo.formModels.RentalIdForm;
import com.example.demo.formModels.RentalObject;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
    public ModelAndView rent(Model model, @ModelAttribute @Valid RentalIdForm rentalIdForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            model.addAttribute("rentalIdForm",new RentalIdForm());
            return new ModelAndView("rental_management/RentAMovie").addObject("rentalIdForm");
        }
        RentalId rentalId=new RentalId();

        rentalId.setSocialSecurityNumber(rentalIdForm.getSocialSecurityNumber());
        rentalId.setRentalDate(rentalIdForm.getRentalDate());

        Movie movie=movieRepository.findById(rentalIdForm.getMovieId()).get();
        if(movie.getAvailable())
            rentalId.setMovieId(rentalIdForm.getMovieId());
        else{
            model.addAttribute("rentalIdForm",new RentalIdForm());
            return new ModelAndView("rental_management/RentAMovie").addObject("rentalIdForm")
                    .addObject("message", "Movie not available. Please choose another one.");
        }
        List<Rental> rentalList=(List<Rental>) rentalRepository.findAll();
        List<Rental> custRentals=rentalList.stream()
                .filter(rental -> rental.getRentalId().getSocialSecurityNumber().equals(rentalIdForm.getSocialSecurityNumber()))
                .collect(Collectors.toList());
        List<String> custMovieNames=new ArrayList<>();
        custRentals.stream()
                .forEach(rental -> custMovieNames.add(movieRepository.findById(rental.getRentalId().getMovieId()).get().getName()));

        if(custMovieNames.contains(movie.getName()))
        {
            model.addAttribute("rentalIdForm",new RentalIdForm());
            return new ModelAndView("rental_management/RentAMovie").addObject("rentalIdForm")
                    .addObject("message", "You have already rented a copy of this movie.");
        }

        movie.setAvailable(false);
        Customer customer=customerRepository.findById(rentalIdForm.getSocialSecurityNumber()).get();
        Rental rental= new Rental(movie, customer, rentalId);

        rentalRepository.save(rental);
        return new ModelAndView("rental_management/RentAgain").addObject("rentalId",rentalId);

    }

    @RequestMapping("notReturned")
    public ModelAndView notReturned(@RequestParam(defaultValue = "1") int page){
        List<Rental> rentalList=(List<Rental>) rentalRepository.findAll();
        List<RentalObject> notReturnedMovies=new ArrayList<>();

        for (Rental rental: rentalList) {
            RentalObject rentalObject=new RentalObject();
            rentalObject.setRentalDate(rental.getRentalId().getRentalDate());
            rentalObject.setMovieId(rental.getRentalId().getMovieId());
            rentalObject.setMovieName(movieRepository.findById(rental.getRentalId().getMovieId()).get().getName());
            rentalObject.setCustomerName(customerRepository.findById(rental.getRentalId().getSocialSecurityNumber()).get().getName());
            rentalObject.setCustomerPhone(customerRepository.findById(rental.getRentalId().getSocialSecurityNumber()).get().getPhone());
            notReturnedMovies.add(rentalObject);

        }
        return pagedViewRental(page,notReturnedMovies, "Not returned movies", "notReturned");
    }

    @RequestMapping("overdueMovies")
    public ModelAndView overdueMovies(@RequestParam(defaultValue = "1") int page){

        List<Rental> rentalList=(List<Rental>) rentalRepository.findAll();
        List<RentalId> overdueRentals=new ArrayList<>();
        List<RentalObject> overdues=new ArrayList<>();

        rentalList.stream()
                .filter(rental -> (new Date().getTime() - rental.getRentalId().getRentalDate().getTime())/(24*60*60*1000)>1)
                .forEach(rental -> overdueRentals.add(rental.getRentalId()));


        for (RentalId rentalId:overdueRentals) {

            RentalObject rentalObject=new RentalObject();
            rentalObject.setRentalDate(rentalId.getRentalDate());
            rentalObject.setMovieId(rentalId.getMovieId());
            rentalObject.setMovieName(movieRepository.findById(rentalId.getMovieId()).get().getName());
            rentalObject.setCustomerName(customerRepository.findById(rentalId.getSocialSecurityNumber()).get().getName());
            rentalObject.setCustomerPhone(customerRepository.findById(rentalId.getSocialSecurityNumber()).get().getPhone());
            overdues.add(rentalObject);
        }

        return pagedViewRental(page, overdues, "Overdue movies", "overdueMovies");
    }

    public ModelAndView pagedViewRental(int page, List<RentalObject> rentalObjects, String title, String listCategory){

        int pageSize=5;
        int last=page*pageSize;
        int numberOfPages= (int)Math.ceil((double) rentalObjects.size()/pageSize);

        if(rentalObjects.size()>=last)
            last=page*pageSize;
        else
            last=rentalObjects.size();
        List<RentalObject> rentals=rentalObjects.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("rental_management/RentalList").addObject("page",page);
        listModel.addObject("rentals",rentals).addObject("title", title);

        if(page>1) {
            listModel.addObject("previous", listCategory+"?page=" + (page - 1));
            listModel.addObject("text1", "previous");
        }
        if(page<numberOfPages) {
            listModel.addObject("next", listCategory+"?page=" + (page + 1));
            listModel.addObject("text2", "next");
        }
        return listModel;
    }

}
