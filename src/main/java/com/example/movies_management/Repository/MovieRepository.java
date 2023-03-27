package com.example.movies_management.Repository;

import com.example.movies_management.Model.Movie;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie,Long> {

    List<Movie> findAllByGenreContainingIgnoreCaseOrderByReleaseYearDesc(String genre);
}
