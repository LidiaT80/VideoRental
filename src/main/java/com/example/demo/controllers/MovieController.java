package com.example.demo.controllers;

import com.example.demo.entities.Movie;
import com.example.demo.formModels.MovieDataForm;
import com.example.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.lang.Long;
@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping("moviePage")
    public ModelAndView moviePage(){
        return new ModelAndView("video_management/MoviePage");
    }

    @RequestMapping("movieData")
    public ModelAndView movieData(Model model){
        model.addAttribute("movieDataForm", new MovieDataForm());
        return new ModelAndView("video_management/EnterMovieData").addObject("movieDataForm");
    }

    @RequestMapping("addMovie")
    public ModelAndView addMovie(@ModelAttribute MovieDataForm movieDataForm){
        Movie movie=new Movie();
        movie.setName(movieDataForm.getName());
        movie.setDescription(movieDataForm.getDescription());
        movie.setReleaseDate(movieDataForm.getReleaseDate());
        movie.setCategory(movieDataForm.getCategory());
        movie.setMovieFormat(movieDataForm.getMovieFormat());
        movieRepository.save(movie);
        return new ModelAndView("video_management/ShowMovieData").addObject("movie", movie);
    }


    @RequestMapping("deleteMovie")
    public ModelAndView deleteMovie(){
        return new ModelAndView("video_management/DeleteMovie");
    }

    @RequestMapping("deleteById")
    public ModelAndView deleteById(@RequestParam("movieId") Long id){

        movieRepository.deleteById(id);
        return new ModelAndView("video_management/MoviePage");

    }

    @RequestMapping("showMovieList")
    public ModelAndView showMovieList(){
        Iterable<Movie> movies=movieRepository.findAll();
        return new ModelAndView("video_management/MovieList").addObject("movies", movies);
    }



}
