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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    ModelMapper mapper=new ModelMapper();

    @RequestMapping("addCustomer")
    public ModelAndView addCustomer(@ModelAttribute @Valid CustomerDataForm customerDataForm,
                                    BindingResult bindingResult, Model model, HttpSession session) {

        if(bindingResult.hasErrors()){
            model.addAttribute("customerDataForm", customerDataForm);
            return new ModelAndView("customer_management/EnterCustomerData").addObject("customerDataForm");
        }

        Customer customer=mapper.map(customerDataForm, Customer.class);
        customerRepository.save(customer);
        return new ModelAndView("customer_management/ShowCustomerData")
                .addObject("customer", customer)
                .addObject("username", session.getAttribute("user"));

    }


    @RequestMapping("customerData")
    public ModelAndView customerData(Model model, HttpSession session){
        model.addAttribute("customerDataForm", new CustomerDataForm());
        return new ModelAndView("customer_management/EnterCustomerData")
                .addObject("customerDataForm")
                .addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("editData")
    public ModelAndView editData(HttpSession session){

        return new ModelAndView("customer_management/EditCustomerData")
                .addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("editCustomer")
    public ModelAndView editCustomer(@RequestParam("socialSecurityNumber") String socialSecurityNumber,
                                     @RequestParam("property") String property,
                                     @RequestParam("newValue") String newValue,
                                     HttpSession session){

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

        return new ModelAndView("customer_management/ShowCustomerData")
                .addObject("customer", customer)
                .addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("showList")
    public ModelAndView showList(@RequestParam (defaultValue = "1") int page, HttpSession session){

        int pageSize=5;
        int last=page*pageSize;
        List<Customer> customerList=(List<Customer>) customerRepository.findAll();
        int numberOfPages= (int)Math.ceil((double) customerList.size()/pageSize);
        if(customerList.size()>=last)
            last=page*pageSize;
        else
            last=customerList.size();
        List<Customer> customers=customerList.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("customer_management/CustomerList")
                .addObject("page",page)
                .addObject("username", session.getAttribute("user"));

        listModel.addObject("customers",customers);

        if(page>1)
            listModel.addObject("previous", "showList?page=" + (page - 1));

        if(page<numberOfPages)
            listModel.addObject("next", "showList?page=" + (page + 1));

        return listModel;

    }

}
