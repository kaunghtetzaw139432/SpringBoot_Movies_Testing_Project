package com.example.Testing.ControllerTest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;

import com.example.Testing.models.Movies;
import com.example.Testing.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class MovieControllerTest {

    @MockBean
    private MovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        titanicMovie.setId(2L);
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleasedDate(LocalDate.of(1999, Month.MAY, 20));

    }

    @Test
    void shouldCreateMovie() throws Exception {

        when(movieService.save(any(Movies.class)))
                .thenReturn(avatarMovie);

        mockMvc.perform(
                post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())))
                .andExpect(jsonPath("$.releasedDate", is(avatarMovie.getReleasedDate().toString())));

    }

    @Test
    void shouldFetchAllMovies() throws Exception {

        Movies titanicMovie = new Movies();
        titanicMovie.setName("titanic");
        titanicMovie.setGenera("Romance");
        titanicMovie.setReleasedDate(LocalDate.of(1999, Month.MAY, 20));
        List<Movies> list = new ArrayList<>();
        list.add(titanicMovie);
        list.add(avatarMovie);
        when(movieService.getAllMovies()).thenReturn(list);
        this.mockMvc
                .perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void shouldFetchMovieById() throws Exception {

        when(movieService.getMoviesById(anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(get("/movies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())))
                .andExpect(jsonPath("$.releasedDate", is(avatarMovie.getReleasedDate().toString())));
    }

    @Test
    void shouldDeleteMovies() throws Exception {

        doNothing().when(movieService).deleteMovie(anyLong());
        this.mockMvc.perform(delete("/movies/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateMovies() throws JsonProcessingException, Exception {

        when(movieService.updateMovies(any(Movies.class), anyLong())).thenReturn(avatarMovie);

        this.mockMvc.perform(put("/movies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie)))
                .andExpect(jsonPath("$.name", is(avatarMovie.getName())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));

    }
}
