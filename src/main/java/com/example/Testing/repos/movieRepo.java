package com.example.Testing.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Testing.models.Movies;

@Repository
public interface movieRepo extends JpaRepository<Movies, Long> {
    List<Movies> findByGenera(String genera);
}
