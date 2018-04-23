package com.example.demo.controllers;

import com.example.demo.entities.Movie;
import com.example.demo.formModels.MovieDataForm;
import com.example.demo.repositories.MovieRepository;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
import java.lang.Long;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    ModelMapper mapper=new ModelMapper();

    @RequestMapping("movieData")
    public ModelAndView movieData(Model model, HttpSession session){
        model.addAttribute("movieDataForm", new MovieDataForm());
        return new ModelAndView("video_management/EnterMovieData").addObject("movieDataForm")
                .addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("addMovie")
    public ModelAndView addMovie(@ModelAttribute @Valid MovieDataForm movieDataForm, BindingResult bindingResult,
                                 Model model, HttpSession session){

        if(bindingResult.hasErrors()){
            model.addAttribute("movieDataForm", movieDataForm);
            return new ModelAndView("video_management/EnterMovieData").addObject("movieDataForm")
                    .addObject("username", session.getAttribute("user"));
        }

        Movie movie=mapper.map(movieDataForm, Movie.class);
        movieRepository.save(movie);
        return new ModelAndView("video_management/ShowMovieData").addObject("movie", movie)
                .addObject("username", session.getAttribute("user"));
    }


    @RequestMapping("deleteMovie")
    public ModelAndView deleteMovie(HttpSession session){
        return new ModelAndView("video_management/DeleteMovie").addObject("username", session.getAttribute("user"));
    }

    @RequestMapping("deleteById")
    public ModelAndView deleteById(@RequestParam (defaultValue = "1") int page, @RequestParam("movieId") Long id, HttpSession session){

        movieRepository.deleteById(id);
        List<Movie> movieList=(List<Movie>) movieRepository.findAll();

        return pagedView(page, movieList, "All movies","showMovieList", session );
    }

    @RequestMapping("showMovieList")
    public ModelAndView showMovieList(@RequestParam (defaultValue = "1") int page, HttpSession session){

        List<Movie> movieList=(List<Movie>) movieRepository.findAll();

        return pagedView(page, movieList, "All movies","showMovieList", session );

    }


    @RequestMapping("availableMovies")
    public ModelAndView availableMovies(@RequestParam (defaultValue = "1") int page, HttpSession session){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getAvailable())
                .collect(Collectors.toList());

        return pagedView(page, movieList, "Available movies", "availableMovies", session);
    }

    @RequestMapping("findMovie")
    public ModelAndView findMovie(HttpSession session){
        return new ModelAndView("video_management/FindMovie")
                .addObject("username", session.getAttribute("user"));

    }


    @RequestMapping("findByName")
    public ModelAndView findByName(@RequestParam String movieName, @RequestParam (defaultValue = "1") int page,
                                   HttpSession session){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(movieName))
                .collect(Collectors.toList());

       return pagedView(page, movieList, "Search movies by name", "findByName", session);
    }

    @RequestMapping("findByCategory")
    public ModelAndView findByCategory(@RequestParam String movieCategory, @RequestParam (defaultValue = "1") int page,
                                       HttpSession session){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getCategory().equalsIgnoreCase(movieCategory))
                .collect(Collectors.toList());
        return pagedView(page, movieList, "Search movies by category", "findByCategory", session);
    }

    public ModelAndView pagedView(int page, List<Movie> movieList, String title, String listCategory, HttpSession session){

        int pageSize=5;
        int last=page*pageSize;
        int numberOfPages= (int)Math.ceil((double) movieList.size()/pageSize);

        if(movieList.size()>=last)
            last=page*pageSize;
        else
            last=movieList.size();
        List<Movie> movies=movieList.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("video_management/MovieList").addObject("page",page);
        listModel.addObject("movies",movies).addObject("title", title)
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
