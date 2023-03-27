package com.example.movies_management.Service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.movies_management.Exception.MoviesAlreadyExistException;
import com.example.movies_management.Model.Movie;
import com.example.movies_management.Repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains test methods for MovieServiceImpl class.
 * It uses Mockito framework to mock MovieRepository
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DataMongoTest
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;

    /**
     * Sets up the mock MovieRepository and MovieServiceImpl for testing.
     */
    @BeforeEach
    public void setUp() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepository);
    }

    /**
     * Tests the getRecommendedMoviesByGenre method which returns movies with given genre.
     */
    @Test
    public void testGetRecommendedMoviesByGenre() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1L, "Movie 1", "Action", 2020, "Director 1"));
        movies.add(new Movie(2L, "Movie 2", "Action", 2021, "Director 2"));
        when(movieRepository.findAllByGenreContainingIgnoreCaseOrderByReleaseYearDesc("Action")).thenReturn(movies);
        List<Movie> recommendedMovies = movieService.getRecommendedMoviesByGenre("Action");
        assertEquals(2, recommendedMovies.size());
    }

    /**
     * Tests the saveMovie method which saves a new movie and throws MoviesAlreadyExistException if the movie already exists.
     *
     * @throws MoviesAlreadyExistException if the movie already exists.
     */
    @Test
    public void testSaveMovie() throws MoviesAlreadyExistException {
        Movie movieToSave = new Movie(null, "Movie x", "Comedy", 2020, "Director x");
        Movie savedMovie = new Movie(1L, "Movie x", "Comedy", 2020, "Director x");
        when(movieRepository.findById(movieToSave.getId())).thenReturn(Optional.empty());
        when(movieRepository.findAll()).thenReturn(new ArrayList<Movie>());
        when(movieRepository.save(movieToSave)).thenReturn(savedMovie);
        Movie returnedMovie = movieService.saveMovie(movieToSave);
        assertEquals(1L, returnedMovie.getId().longValue());
        assertEquals("Movie x", returnedMovie.getTitle());
        assertEquals("Comedy", returnedMovie.getGenre());
    }

    /**
     * Tests the saveExistingMovie method which throws MoviesAlreadyExistException if the movie already exists.
     */
    @Test
    public void testSaveExistingMovie() {
        Movie movieToSave = new Movie(1L, "Existing Movie", "Drama", 1994, "Director y");
        when(movieRepository.findById(movieToSave.getId())).thenReturn(Optional.of(movieToSave));
        assertThrows(MoviesAlreadyExistException.class, () -> movieService.saveMovie(movieToSave));
    }

    /**
     * This method tests the retrieval of all movies and asserts that the returned list has a size of 2.
     */
    @Test
    public void testGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1L, "Movie 1", "Action", 2020, "Director 1"));
        movies.add(new Movie(2L, "Movie 2", "Action", 2021, "Director 2"));
        when(movieRepository.findAll()).thenReturn(movies);
        List<Movie> allMovies = movieService.getAllMovies();
        assertEquals(2, allMovies.size());
    }
}