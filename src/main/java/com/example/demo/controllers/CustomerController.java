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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("addCustomer")
    public ModelAndView addCustomer(@ModelAttribute @Valid CustomerDataForm customerDataForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("customerDataForm", customerDataForm);
            return new ModelAndView("customer_management/EnterCustomerData").addObject("customerDataForm");
        }

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
    public ModelAndView showList(@RequestParam (defaultValue = "1") int page){

        int pageSize=5;
        int last=page*pageSize;
        List<Customer> customerList=(List<Customer>) customerRepository.findAll();
        int numberOfPages= (int)Math.ceil((double) customerList.size()/pageSize);
        if(customerList.size()>=last)
            last=page*pageSize;
        else
            last=customerList.size();
        List<Customer> customers=customerList.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("customer_management/CustomerList").addObject("page",page);

        listModel.addObject("customers",customers);

        if(page>1) {
            listModel.addObject("previous", "showList?page=" + (page - 1));
            listModel.addObject("text1", "previous");
        }

        if(page<numberOfPages) {
            listModel.addObject("next", "showList?page=" + (page + 1));
            listModel.addObject("text2", "next");
        }

        return listModel;

    }




}
