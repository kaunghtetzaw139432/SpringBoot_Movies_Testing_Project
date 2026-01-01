package com.example.Testing.models;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movies {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String genera;
  private LocalDate releasedDate;

  // Empty Constructor
  public Movies() {
  }

  // Constructor with all fields
  public Movies(Long id, String name, String genera, LocalDate releasedDate) {
    this.id = id;
    this.name = name;
    this.genera = genera;
    this.releasedDate = releasedDate;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGenera() {
    return genera;
  }

  public void setGenera(String genera) {
    this.genera = genera;
  }

  public LocalDate getReleasedDate() {
    return releasedDate;
  }

  public void setReleasedDate(LocalDate releasedDate) {
    this.releasedDate = releasedDate;
  }
}