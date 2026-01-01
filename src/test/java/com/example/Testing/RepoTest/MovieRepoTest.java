package com.example.Testing.RepoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.Testing.models.Movies;
import com.example.Testing.repos.movieRepo;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest

public class MovieRepoTest {
    @Autowired
    private movieRepo movieRepo;
    private Movies avatarMovie;
    private Movies titanicMovie;

    @BeforeEach
    void init() {
        avatarMovie = new Movies();
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleasedDate(LocalDate.of(2000, Month.APRIL, 22));


         titanicMovie = new Movies();
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleasedDate(LocalDate.of(1999, Month.MAY, 20));
      
    }

    @Test
    @DisplayName("It should save the movie to the database")
    void save() {
        // Arrange

        // Act
        Movies newMovies = movieRepo.save(avatarMovie);
        // Assert
        assertNotNull(newMovies);
        assertThat(newMovies.getId()).isNotEqualTo(null);

    }

    @Test
    @DisplayName("It should return the movies list with size of 2")
    void getAllMovies() {
        movieRepo.save(avatarMovie);
        movieRepo.save(titanicMovie);

        List<Movies> list = movieRepo.findAll();
        assertNotNull(list);
        assertThat(list).isNotNull();
        assertEquals(2, list.size());

    }

    @Test
    @DisplayName("It should return the movie by its id")
    void getMoviesById() {
        movieRepo.save(avatarMovie);
        Movies existingMovies = movieRepo.findById(avatarMovie.getId()).get();
        assertNotNull(existingMovies);
        assertEquals("Action", existingMovies.getGenera());
        assertThat(avatarMovie.getReleasedDate()).isBefore(LocalDate.of(2000, Month.APRIL, 23));
    }

    @Test
    @DisplayName("It should update the movie with genera FANTACY")
    void updateMovie() {
        movieRepo.save(avatarMovie);

        Movies existingMovies = movieRepo.findById(avatarMovie.getId()).get();
        existingMovies.setGenera("Fantancy");
        Movies newMovies = movieRepo.save(existingMovies);
        assertEquals("Fantancy", newMovies.getGenera());
        assertEquals("Avatar", newMovies.getName());
    }

    @Test
    @DisplayName("It should delete the existing movies")
    void deleteMovie() {
        movieRepo.save(avatarMovie);
        Long id = avatarMovie.getId();
        movieRepo.save(titanicMovie);
        movieRepo.delete(avatarMovie);
        Optional<Movies> existMovies = movieRepo.findById(id);
        List<Movies> list = movieRepo.findAll();

        assertEquals(1, list.size());
        assertThat(existMovies).isEmpty();
    }

    @Test
    @DisplayName("It should return the movies list with genera ROMANCE")
    void getMoviesByGenera() {
        movieRepo.save(avatarMovie);
        movieRepo.save(titanicMovie);

        List<Movies> list = movieRepo.findByGenera("Romance");
        assertNotNull(list);
        assertThat(list.size()).isEqualTo(1);
    }
}
