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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
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

    ModelMapper mapper=new ModelMapper();

    @RequestMapping("rentalPage")
    public ModelAndView rentalPage(HttpSession session){
        return new ModelAndView("rental_management/RentalPage").addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("renting")
    public ModelAndView rentAMovie(Model model, HttpSession session){
        model.addAttribute("rentalIdForm",new RentalIdForm());
        return new ModelAndView("rental_management/RentAMovie")
                .addObject("rentalIdForm").addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("rent")
    public ModelAndView rent(Model model, @ModelAttribute @Valid RentalIdForm rentalIdForm,
                             BindingResult bindingResult, HttpSession session){

        if(bindingResult.hasErrors()){
            model.addAttribute("rentalIdForm",rentalIdForm);
            return new ModelAndView("rental_management/RentAMovie")
                    .addObject("rentalIdForm").addObject("username", session.getAttribute("user"));
        }

        RentalId rentalId;
        Movie movie=movieRepository.findById(rentalIdForm.getMovieId()).get();
        if(movie.getAvailable())
             rentalId=mapper.map(rentalIdForm, RentalId.class);
        else{
            model.addAttribute("rentalIdForm",new RentalIdForm());
            return new ModelAndView("rental_management/RentAMovie").addObject("rentalIdForm")
                    .addObject("message", "Movie not available. Please choose another one.")
                    .addObject("username", session.getAttribute("user"));
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
                    .addObject("message", "You have already rented a copy of this movie.")
                    .addObject("username", session.getAttribute("user"));
        }

        movie.setAvailable(false);
        Customer customer=customerRepository.findById(rentalIdForm.getSocialSecurityNumber()).get();
        Rental rental= new Rental(movie, customer, rentalId);

        rentalRepository.save(rental);
        return new ModelAndView("rental_management/RentAgain")
                .addObject("rentalId",rentalId).addObject("username", session.getAttribute("user"));

    }

    @RequestMapping("notReturned")
    public ModelAndView notReturned(@RequestParam(defaultValue = "1") int page, HttpSession session){
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
        return pagedViewRental(page,notReturnedMovies, "Not returned movies", "notReturned", session);
    }

    @RequestMapping("overdueMovies")
    public ModelAndView overdueMovies(@RequestParam(defaultValue = "1") int page, HttpSession session){

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

        return pagedViewRental(page, overdues, "Overdue movies", "overdueMovies", session);
    }

    @RequestMapping("customersCurrentMovies")
    public ModelAndView customersCurrentMovies(@RequestParam String socialSecurityNumber, @RequestParam(defaultValue = "1") int page,
                                               HttpSession session){
        MovieController movieController=new MovieController();

        List<Rental> rentals=(List<Rental>) rentalRepository.findAll();
        List<Movie> currentMovies=new ArrayList<>();
        rentals.stream()
                .filter(rental -> rental.getRentalId().getSocialSecurityNumber().equals(socialSecurityNumber))
                .forEach(rental ->currentMovies.add(movieRepository.findById(rental.getRentalId().getMovieId()).get()) );
        return movieController.pagedView(page, currentMovies, "Customers current movies", "customersCurrentMovies", session);
    }

    public ModelAndView pagedViewRental(int page, List<RentalObject> rentalObjects, String title,
                                        String listCategory, HttpSession session){

        int pageSize=5;
        int last=page*pageSize;
        int numberOfPages= (int)Math.ceil((double) rentalObjects.size()/pageSize);

        if(rentalObjects.size()>=last)
            last=page*pageSize;
        else
            last=rentalObjects.size();
        List<RentalObject> rentals=rentalObjects.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("rental_management/RentalList").addObject("page",page);
        listModel.addObject("rentals",rentals).addObject("title", title)
                .addObject("username", session.getAttribute("user"));

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
