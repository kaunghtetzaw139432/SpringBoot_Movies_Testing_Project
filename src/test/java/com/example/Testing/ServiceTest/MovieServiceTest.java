package com.example.Testing.ServiceTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Testing.models.Movies;
import com.example.Testing.repos.movieRepo;
import com.example.Testing.services.MovieService;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;
    @Mock
    private movieRepo movieRepo;
    private Movies avatarMovie;
    private Movies titanicMovie;

    @BeforeEach
    void init() {
        avatarMovie = new Movies();
        avatarMovie.setId(1L);
        avatarMovie.setName("Avatar");
        avatarMovie.setGenera("Action");
        avatarMovie.setReleasedDate(LocalDate.of(2000, Month.APRIL, 22));

        titanicMovie = new Movies();
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleasedDate(LocalDate.of(1999, Month.MAY, 20));

    }

    @Test
    @DisplayName("Should save the movie object to database")
    void save() {
        when(movieRepo.save(any(Movies.class))).thenReturn(avatarMovie);
        Movies newMovies = movieService.save(avatarMovie);
        assertNotNull(newMovies);
        assertThat(newMovies.getName()).isEqualTo("Avatar");
    }

    @Test
    @DisplayName("Should return list of movies with size 2")
    void getMovies() {
        List<Movies> list = new ArrayList<>();
        list.add(titanicMovie);
        list.add(avatarMovie);
        when(movieRepo.findAll()).thenReturn(list);
        List<Movies> movies = movieService.getAllMovies();
        assertEquals(2, movies.size());
        assertNotNull(movies);
    }

    @Test
    @DisplayName("Should return the Movie object")
    void getMovieById() {
        when(movieRepo.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        Movies existingMovies = movieService.getMoviesById(1L);
        assertNotNull(existingMovies);
        assertThat(existingMovies.getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("Should throw the Exception")
    void getMovieByIdForException() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(avatarMovie));
        assertThrows(RuntimeException.class, () -> {
            movieService.getMoviesById(2L);
        });

    }

    @Test
    @DisplayName("Should update the movie into the database")
    void updateMovie() {
        when(movieRepo.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        when(movieRepo.save(any(Movies.class))).thenReturn(avatarMovie);
        avatarMovie.setGenera("Fantancy");
        Movies updateMoives = movieService.updateMovies(avatarMovie, 1L);
        assertNotNull(updateMoives);
        assertEquals("Fantancy", updateMoives.getGenera());
    }

    @Test
    @DisplayName("Should delete the movie from database")
    void deleteMovie() {
        when(movieRepo.findById(anyLong())).thenReturn(Optional.of(avatarMovie));
        doNothing().when(movieRepo).delete(any(Movies.class));
        movieService.deleteMovie(1L);
        verify(movieRepo, times(1)).delete(avatarMovie);
    }
}
