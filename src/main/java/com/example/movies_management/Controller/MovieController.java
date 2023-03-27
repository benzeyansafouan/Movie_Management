package com.example.movies_management.Controller;


import com.example.movies_management.Exception.MoviesAlreadyExistException;
import com.example.movies_management.Model.Movie;
import com.example.movies_management.Service.MovieService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for handling requests related to movies
 */
@RestController
@RequestMapping("/api")
public class MovieController {

    /**
     * The service for handling movie-related operations
     */
    private final MovieService movieService;


    /**
     Constructor for creating a new MovieController instance.
     @param movieService The service for handling movie-related operations.
     */
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**

     Get all the movies.
     @return A response entity with a list of all the movies.
     */
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        var movies= movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    /**

     Get a list of recommended movies by genre.
     @param genre The genre of movies to recommend.
     @return A response entity with a list of recommended movies.
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<Movie>> getRecommendedMovies(@RequestParam String genre) {
        var moviesList = movieService.getRecommendedMoviesByGenre(genre);
        return ResponseEntity.ok(moviesList);
    }

    /**
     Save a movie.
     @param movie The movie to save.
     @return A response entity with the saved movie or a bad request if the movie already exists.
     */
    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovies(@RequestBody Movie movie) {
        try {
            var savedMovie = movieService.saveMovie(movie);
            return ResponseEntity.ok(savedMovie);
        } catch (MoviesAlreadyExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
