package com.example.demo.controllers;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Movie;
import com.example.demo.entities.Rental;
import com.example.demo.entities.ReturnedMovie;
import com.example.demo.formModels.CustomerDataForm;
import com.example.demo.repositories.CustomerRepository;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.internal.SessionFactoryServiceRegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("addCustomer")
    public ModelAndView addCustomer( @ModelAttribute CustomerDataForm customerDataForm ) {

        Customer customer=new Customer(customerDataForm.getSocialSecurityNumber(), customerDataForm.getAddress(), customerDataForm.getCity(),
                customerDataForm.getCountry(), customerDataForm.getEmail(),customerDataForm.getName(), customerDataForm.getPhone(), customerDataForm.getPostalCode() );
        customerRepository.save(customer);
        return new ModelAndView("customer_management/ShowCustomerData").addObject("customer", customer);

    }


    @RequestMapping("customerData")
    public ModelAndView customerData(Model model){
        model.addAttribute("customerDataForm", new CustomerDataForm());
        return new ModelAndView("customer_management/EnterCustomerData").addObject("customerDataForm");
    }

    @RequestMapping("customersPage")
    public ModelAndView customersPage(){
        return new ModelAndView("customer_management/CustomersPage");
    }

    @RequestMapping("editData")
    public ModelAndView editData(){

        return new ModelAndView("customer_management/EditCustomerData");
    }

    @RequestMapping("editCustomer")
    public ModelAndView editCustomer(@RequestParam("socialSecurityNumber") String socialSecurityNumber,
                                     @RequestParam("property") String property, @RequestParam("newValue") String newValue){

        Customer customer=customerRepository.findById(socialSecurityNumber).get();

        switch (property){
            case "name":
                customer.setName(newValue);
                break;
            case "address":
                customer.setAddress(newValue);
                break;
            case "postalCode":
                customer.setPostalCode(newValue);
                break;
            case "city":
                customer.setCity(newValue);
                break;
            case "country":
                customer.setCountry(newValue);
                break;
            case "phone":
                customer.setPhone(newValue);
                break;
            case "email":
                customer.setEmail(newValue);
                break;
        }

        customerRepository.save(customer);

        return new ModelAndView("customer_management/ShowCustomerData").addObject("customer", customer);
    }

    @RequestMapping("showList")
    public ModelAndView showList(){
        Iterable<Customer> customers=customerRepository.findAll();
        return new ModelAndView("customer_management/CustomerList").addObject("customers", customers);

    }


}
