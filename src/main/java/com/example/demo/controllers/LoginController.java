package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("home")
    public ModelAndView loginPage(){

        return new ModelAndView("login_management/LogInPage");
    }

    @RequestMapping("startPage")
    public ModelAndView startPage(HttpSession session, HttpServletRequest request){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(username!=null)
            session.setAttribute("user",username);
        if((session.getAttribute("user").equals("user1") && password.equals("123")) ||
                (session.getAttribute("user").equals("user2") && password.equals("456")))
            return new ModelAndView("login_management/StartPage").addObject("username", session.getAttribute("user"));
        else
            return new ModelAndView("login_management/LogInPage");
    }
    @RequestMapping("logOut")
    public ModelAndView logOut(HttpSession session){
        session.invalidate();
        return new ModelAndView("login_management/LogInPage");
    }

    @RequestMapping("goBack")
    public ModelAndView goBack(HttpSession session){
        return new ModelAndView("login_management/StartPage")
                .addObject("username", session.getAttribute("user"));
    }
}
