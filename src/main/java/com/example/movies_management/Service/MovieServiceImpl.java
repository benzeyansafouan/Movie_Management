package com.example.movies_management.Service;

import com.example.movies_management.Exception.MoviesAlreadyExistException;
import com.example.movies_management.Model.Movie;
import com.example.movies_management.Repository.MovieRepository;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 Implementation of the MovieService interface,
 responsible for providing methods to interact with the MovieRepository
 */
@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    /**
     Constructor for the MovieServiceImpl class,
     receives a MovieRepository object as parameter to be used internally
     @param movieRepository object responsible for handling the database interactions for
     Movie objects
     */
    public MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository=movieRepository;
    }

    /**
     Retrieves a list of movies with the given genre, sorted by release year in descending order
     @param genre String containing the genre to be used as filter
     @return List of movies that match the genre and sorted by release year in descending order
     */
    @Override
    public List<Movie> getRecommendedMoviesByGenre(String genre) {
        return movieRepository.findAllByGenreContainingIgnoreCaseOrderByReleaseYearDesc(genre);
    }

    /**

     Saves a new movie into the database, or updates an existing one
     @param movie Movie object to be saved or updated in the database
     @return Movie object that was saved or updated in the database
     @throws MoviesAlreadyExistException if the movie with the same ID already exists in the database
     */
    @Override
    public Movie saveMovie(Movie movie) throws MoviesAlreadyExistException {
        var existedMovies=movieRepository.findById(movie.getId());
        if (existedMovies.isPresent()){
            throw new MoviesAlreadyExistException();
        }else {
            var movieId=new Long(movieRepository.findAll().size())+1;
            movie.setId(movieId);
            return movieRepository.save(movie);
        }
    }

    /**
     Retrieves all movies stored in the database
     @return List of all Movie objects stored in the database
     */
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
