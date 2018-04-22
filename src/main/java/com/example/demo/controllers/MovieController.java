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
import java.util.List;
import java.util.stream.Collectors;

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
    public ModelAndView showMovieList(@RequestParam (defaultValue = "1") int page){

        List<Movie> movieList=(List<Movie>) movieRepository.findAll();

        return pagedView(page, movieList, "All movies","showMovieList" );

    }


    @RequestMapping("availableMovies")
    public ModelAndView availableMovies(@RequestParam (defaultValue = "1") int page){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getAvailable())
                .collect(Collectors.toList());

        return pagedView(page, movieList, "Available movies", "availableMovies");
    }


    @RequestMapping("findByName")
    public ModelAndView findByName(@RequestParam String movieName, @RequestParam (defaultValue = "1") int page){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(movieName))
                .collect(Collectors.toList());
       return pagedView(page, movieList, "Search movies by name", "findByName");
    }

    @RequestMapping("findByCategory")
    public ModelAndView findByCategory(@RequestParam String movieCategory, @RequestParam (defaultValue = "1") int page){
        List<Movie> movies=(List<Movie>) movieRepository.findAll();
        List<Movie> movieList=movies.stream()
                .filter(movie -> movie.getCategory().equalsIgnoreCase(movieCategory))
                .collect(Collectors.toList());
        return pagedView(page, movieList, "Search movies by category", "findByCategory");
    }

    public ModelAndView pagedView(int page, List<Movie> movieList, String title, String listCategory){

        int pageSize=5;
        int last=page*pageSize;
        int numberOfPages= (int)Math.ceil((double) movieList.size()/pageSize);

        if(movieList.size()>=last)
            last=page*pageSize;
        else
            last=movieList.size();
        List<Movie> movies=movieList.subList((page-1)*pageSize,last);

        ModelAndView listModel=new ModelAndView("video_management/MovieList").addObject("page",page);
        listModel.addObject("movies",movies).addObject("title", title);

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
