package com.example.reviewMovie.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.reviewMovie.entity.Movie;

public interface MovieRepo {
	
	@Query(value="select id,movie_url,movie_name,movie_year,movie_rating,released,release_date from movie where movie_name like %?1%",nativeQuery = true)
	List<Movie> getMovies(String Keyword);
    
	@Query(value="select id,movie_url,movie_name,movie_year,movie_rating,released,release_date from movie where language=?1",nativeQuery = true)
	List<Movie> getGenreMovieList(String language);
}
