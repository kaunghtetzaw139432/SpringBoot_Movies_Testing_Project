package com.example.Testing.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Testing.models.Movies;
import com.example.Testing.repos.movieRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MovieService {
    private final movieRepo movieRepo;

    public Movies save(Movies movies) {
        return movieRepo.save(movies);
    }

    public List<Movies> getAllMovies() {
        return movieRepo.findAll();
    }

    public Movies getMoviesById(Long id) {
        return movieRepo.findById(id).orElseThrow(() -> new RuntimeException("No movies with that id"));
    }

    public Movies updateMovies(Movies movies, Long id) {
        Movies existingMovie = movieRepo.findById(id).get();
        existingMovie.setGenera(movies.getGenera());
        existingMovie.setName(movies.getName());
        existingMovie.setReleasedDate(movies.getReleasedDate());
        return movieRepo.save(existingMovie);
    }

    public void deleteMovie(Long id) {
        Movies existingMovies = movieRepo.findById(id).get();
        movieRepo.delete(existingMovies);
    }
}
