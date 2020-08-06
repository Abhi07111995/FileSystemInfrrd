package com.example.reviewMovie.service.Implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.example.reviewMovie.bean.LoginResponse;
import com.example.reviewMovie.cache.service.CacheService;
import com.example.reviewMovie.entity.Bookmark;
import com.example.reviewMovie.entity.BookmarkedMovie;
import com.example.reviewMovie.entity.LoginDetails;
import com.example.reviewMovie.entity.Movie;
import com.example.reviewMovie.repo.BookmarkRepo;
import com.example.reviewMovie.repo.BookmarkedMovieRepo;
import com.example.reviewMovie.repo.LoginDetailsRepo;
import com.example.reviewMovie.repo.MovieRepo;
import com.example.reviewMovie.service.ReviewMovieService;
import com.example.reviewMovie.util.AuthUtil;

public class ReviewMovieServiceImplementation implements ReviewMovieService {

	private static Logger LOGGER = LoggerFactory.getLogger(ReviewMovieServiceImplementation.class);
	@Autowired
	LoginDetailsRepo loginDetailsRepo;
	@Autowired
	MovieRepo movieRepo;
	@Autowired
	CacheService cacheService;
	@Autowired
	private Environment environment;
	@Autowired
	private BookmarkRepo bookmarkMovieRepo;
	@Autowired
	private BookmarkedMovieRepo bookmarkedMovieRepo;
	@Override
	public LoginResponse checkAuthorization(String emailId, String password, HttpServletRequest request) {
		String pass = loginDetailsRepo.getPassword(password);
		LoginDetails loginDetails = loginDetailsRepo.getLoginDetails(emailId, pass);
		LoginResponse responseLogin = new LoginResponse();
		if (loginDetails.getPassword() == null || loginDetails == null || loginDetails.getPassword().equals("")) {
			responseLogin.setMessage("failure");
			return responseLogin;
		}
		String loginToken = AuthUtil.createJsonWebToken(loginDetails.getEmail_id());
		Map<String, Object> cacheData = new HashMap<String, Object>();
		cacheData.put("key", "reviewMovie" + loginToken);
		cacheData.put("cacheData", loginToken);
		cacheData.put("expTime", 86400);
		cacheService.setCacheData(cacheData);
		return responseLogin;
	}
	@Override
	public List<Movie> getMovieList(String keyword,HttpServletRequest request) {
		String key = "reviewMovie" + request.getHeader("X-Review-Movie-Session-Token");
		List<Movie> movies = null;
		try {
			movies = (List<Movie>) cacheService.getCacheData(key,
					Integer.valueOf(environment.getProperty("redis.default.database")));
			
		} catch (Exception e) {
			LOGGER.info("session token may have expired");
		}
		if(movies.isEmpty())
		{
			movies = movieRepo.getMovies(keyword);
		}
		return movies;
		
	}
	@Override
	public boolean BookmarkMovie(Bookmark bookmark, HttpServletRequest httpServletRequest) {
		try
		{
			bookmarkMovieRepo.save(bookmark);
		}
		catch(Exception e)
		{
			LOGGER.info(e.getMessage());
		}
		return true;
	}
	@Override
	public List<BookmarkedMovie> getBookmarkedMovie(int userId, HttpServletRequest httpServletRequest) {
		List<BookmarkedMovie>  bookmarkedMovies=null;
		try
		{
			bookmarkedMovies=bookmarkedMovieRepo.getBookmarkedMovie(userId);
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return bookmarkedMovies;
	}
	
}
