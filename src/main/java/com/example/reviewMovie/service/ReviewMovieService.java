package com.example.reviewMovie.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.reviewMovie.bean.LoginResponse;
import com.example.reviewMovie.entity.Bookmark;
import com.example.reviewMovie.entity.BookmarkedMovie;
import com.example.reviewMovie.entity.Movie;

public interface ReviewMovieService {
	
	
	LoginResponse checkAuthorization(String emailId, String password, HttpServletRequest request);

	List<Movie> getMovieList(String keyword, HttpServletRequest httpServletRequest);

	boolean BookmarkMovie(Bookmark bookmark, HttpServletRequest httpServletRequest);

	List<BookmarkedMovie> getBookmarkedMovie(int userId, HttpServletRequest httpServletRequest);

	

}
