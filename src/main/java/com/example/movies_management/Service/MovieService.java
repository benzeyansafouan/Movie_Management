package com.example.movies_management.Service;

import com.example.movies_management.Exception.MoviesAlreadyExistException;
import com.example.movies_management.Model.Movie;
import java.util.List;

public interface MovieService {

    List<Movie> getRecommendedMoviesByGenre(String genre);

    Movie saveMovie(Movie movie) throws MoviesAlreadyExistException;

    List<Movie> getAllMovies();
}
