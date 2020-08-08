package com.example.reviewMovie.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.reviewMovie.entity.BookmarkedMovie;
import com.example.reviewMovie.entity.Movie;


public interface BookmarkedMovieRepo extends PagingAndSortingRepository<BookmarkedMovie,Integer> {
	

	
	
	@Query(value = "SELECT movie_name,movie_url FROM Movie m  " + 
			"INNER JOIN bookmark b ON b.movie_id = m.id  " + 
			"WHERE b.user_id =?1 ", nativeQuery = true)
	List<BookmarkedMovie> getBookmarkedMovie(int userId);

}
