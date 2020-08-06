package com.example.reviewMovie.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.reviewMovie.entity.Movie;

public interface MovieRepo {
	
	@Query(value="select id,movie_url,movie_name,movie_year,movie_rating,released,release_date",nativeQuery = true)
	List<Movie> getMovies(String Keyword);
}
