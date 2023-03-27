package com.example.movies_management.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import com.example.movies_management.Exception.MoviesAlreadyExistException;
import com.example.movies_management.Model.Movie;
import com.example.movies_management.Service.MovieService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

/**
 This class contains test cases for the MovieController class.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DataMongoTest
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    /**
     Set up the mock objects before each test case.
     */
    private void setup(){
        movieService=mock(MovieService.class);
        movieController=new MovieController(movieService);
    }
    /**
     Test case for getting all movies.
     */
    @Test
    void getAllMovies() {
        // Arrange
        this.setup();
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(new Long(1),"Movie 1","action",2020,"director 1"));
        movies.add(new Movie(new Long(2),"Movie 2","drama",2021,"director 2"));
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);
        // Act
        var response = movieController.getAllMovies();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    /**
     Test case for getting recommended movies by genre.
     */
    @Test
    void getRecommendedMovies() {
        // Arrange
        this.setup();
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(new Long(1), "Movie 1", "Action",2020,"director 1"));
        movies.add(new Movie(new Long(2), "Movie 2", "Action",2019,"director 2"));
        Mockito.when(movieService.getRecommendedMoviesByGenre("Action")).thenReturn(movies);
        // Act
        var response = movieController.getRecommendedMovies("Action");
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }


    /**
     Test case for saving a new movie.
     @throws MoviesAlreadyExistException if the movie already exists
     */
    @Test
    void saveMovies() throws MoviesAlreadyExistException {
        // Arrange
        this.setup();
        var movieToSave = new Movie(null, "Movie x", "Comedy",2020,"director x");
        var savedMovie = new Movie(1L, "Movie y", "Comedy",2019,"director y");
        Mockito.when(movieService.saveMovie(movieToSave)).thenReturn(savedMovie);
        // Act
        var response = movieController.saveMovies(movieToSave);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId().longValue());
        assertEquals("Movie y", response.getBody().getTitle());
        assertEquals("Comedy", response.getBody().getGenre());
    }

    /**
     Test case for saving an existing movie.
     @throws MoviesAlreadyExistException if the movie already exists
     */
    @Test
    public void testSaveExistingMovie() throws MoviesAlreadyExistException {
        // Arrange
        this.setup();
        var movieToSave = new Movie(null, "Existing Movie", "Drama",1994,"director y");
        Mockito.when(movieService.saveMovie(movieToSave)).thenThrow(new MoviesAlreadyExistException());
        // Act
        var response = movieController.saveMovies(movieToSave);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}