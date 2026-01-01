package com.example.Testing;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import com.example.Testing.models.Movies;
import com.example.Testing.repos.movieRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestingApplicationTests {
	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private movieRepo movieRepo;

	private Movies savedAvatarMovie;

	private Movies savedTitanicMovie;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void beforeSetup() {
		baseUrl = "http://localhost:" + port + "/movies";
		Movies avatarMovie = new Movies();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleasedDate(LocalDate.of(2000, Month.APRIL, 22));

		Movies titanicMovie = new Movies();
		titanicMovie.setName("titanic");
		titanicMovie.setGenera("Romance");
		titanicMovie.setReleasedDate(LocalDate.of(1999, Month.MAY, 20));

		savedAvatarMovie = movieRepo.save(avatarMovie);
		savedTitanicMovie = movieRepo.save(titanicMovie);
	}

	@AfterEach
	public void afterSetup() {
		movieRepo.deleteAll();
	}

	@Test
	void shouldCreateMovieTest() {
		Movies avatarMovie = new Movies();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Action");
		avatarMovie.setReleasedDate(LocalDate.of(2000, Month.APRIL, 22));
		Movies newMovies = restTemplate.postForObject(baseUrl, avatarMovie, Movies.class);

		assertNotNull(newMovies);
		assertThat(newMovies.getId()).isNotNull();
	}

	@Test
	void shouldFetchMoviesTest() {

		List<Movies> list = restTemplate.getForObject(baseUrl, List.class);
		assertThat(list.size()).isEqualTo(2);
	}

	@Test
	void shouldFetchOneMoviesTest() {
		Movies existingMovies = restTemplate.getForObject(baseUrl + "/" + savedAvatarMovie.getId(), Movies.class);
		assertNotNull(existingMovies);
		assertEquals("Avatar", existingMovies.getName());
	}

	@Test
	void shouldDeleteMovieTest() {
		restTemplate.delete(baseUrl + "/" + savedAvatarMovie.getId());
		int count = movieRepo.findAll().size();
		assertEquals(1, count);

	}

	@Test
	void shouldUpdateMovieTest() {

		// Update Avatar
		savedAvatarMovie.setGenera("Fantacy");
		restTemplate.put(baseUrl + "/{id}", savedAvatarMovie, savedAvatarMovie.getId());

		// Fetch and assert
		Movies existingMovies = restTemplate.getForObject(baseUrl + "/" + savedAvatarMovie.getId(), Movies.class);
		assertNotNull(existingMovies);
		assertEquals("Fantacy", existingMovies.getGenera());
	}

	@Test
	void contextLoads() {
	}

}
