package com.example.reviewMovie.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name="movie")
public class Movie {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String movieUrl;
	String movieName;
	int movieYear;
	int movieRating;
	boolean released;
	Timestamp releaseDate;
	String language;

}
