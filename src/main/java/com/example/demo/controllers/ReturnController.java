package com.example.demo.controllers;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Movie;
import com.example.demo.entities.Rental;
import com.example.demo.entities.ReturnedMovie;
import com.example.demo.entities.compositeKeys.ReturnId;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RentalRepository;
import com.example.demo.repositories.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReturnController {

    @Autowired
    private ReturnRepository returnRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @RequestMapping("movieReturn")
    public ModelAndView movieReturn(HttpSession session){
        return new ModelAndView("rental_management/ReturnMovies").addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("findRentedMovies")
    public ModelAndView findRentedMovies( @RequestParam String socialSecurityNumber, HttpSession session){
        ReturnId returnId=new ReturnId();
        ReturnedMovie returnedMovie=new ReturnedMovie();
        Customer customer=customerRepository.findById(socialSecurityNumber).get();
        List<Rental> rentedMovies=(List<Rental>) rentalRepository.findAll();
        List<Movie> custMovies=new ArrayList<>();
        rentedMovies.stream()
                .filter(rental -> rental.getRentalId().getSocialSecurityNumber().equals(socialSecurityNumber))
                .forEach(rental -> custMovies.add(movieRepository.findById(rental.getRentalId().getMovieId()).get()));

        custMovies.stream()
               .forEach(movie -> { movie.setAvailable(true);
                                   returnId.setMovieId(movie.getMovieId());
                                   returnId.setSocialSecurityNumber(socialSecurityNumber);
                                   returnedMovie.setMovie(movie);
                                   returnedMovie.setCustomer(customer);
                                   returnedMovie.setReturnDate(new Date());
                                   returnedMovie.setReturnId(returnId);
                                   returnRepository.save(returnedMovie); });
        if(custMovies.size()==0)
            return new ModelAndView("rental_management/ReturnMovies")

                    .addObject("message", "Nothing to return!")
                    .addObject("username", session.getAttribute("user"));


        rentedMovies.stream()
                .filter(rental -> rental.getRentalId().getSocialSecurityNumber().equals(socialSecurityNumber))
                .forEach(rental -> rentalRepository.deleteById(rental.getRentalId()));

        return new ModelAndView("rental_management/ReturnMovies")
                .addObject("movies",custMovies )
                .addObject("date", new SimpleDateFormat("yyyy/mm/dd").format(returnedMovie.getReturnDate()))
                .addObject("message", "Movies are returned!")
                .addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("customerHistory")
    public ModelAndView customerHistory(@RequestParam String socialSecurityNumber, @RequestParam(defaultValue = "1") int page,
                                        HttpSession session){
        MovieController movieController=new MovieController();
        List<ReturnedMovie> returnedMovies=(List<ReturnedMovie>) returnRepository.findAll();
        List<Movie> custMovies=new ArrayList<>();

        returnedMovies.stream()
                .filter(returnedMovie -> returnedMovie.getReturnId().getSocialSecurityNumber().equals(socialSecurityNumber))
                .forEach(returnedMovie->custMovies.add(returnedMovie.getMovie()));

        return movieController.pagedView(page, custMovies, "Customers movies", "customerHistory", session);
    }


}
