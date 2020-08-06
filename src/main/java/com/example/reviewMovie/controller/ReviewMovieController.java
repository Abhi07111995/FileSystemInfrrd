package com.example.reviewMovie.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reviewMovie.bean.LoginRequest;
import com.example.reviewMovie.bean.Response;
import com.example.reviewMovie.entity.Bookmark;
import com.example.reviewMovie.service.ReviewMovieService;
import com.example.reviewMovie.util.AuthUtil;

@CrossOrigin
@RestController
public class ReviewMovieController {
	
	@Value("${reviewMovieDashboard.password}")
	private String password;
	@Autowired
	ReviewMovieService reviewMovieService;
	public static final Logger logger = LoggerFactory.getLogger(ReviewMovieController.class);
	@Autowired
	

	@RequestMapping(value="/login",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public Response checkAuthorization(@RequestBody  LoginRequest loginRequest, HttpServletRequest request) throws Exception
	{
		Response response=new Response();
		try
		{
		response.setResult(reviewMovieService.checkAuthorization(loginRequest.getEmailId(),loginRequest.getPassword(),request));
		response.setMsg("successfull");
		response.setSuccess(true);
		return response;
		}
		catch (Exception e) {
		   response.setResult(null);
		   if(e.getMessage()==null)
			   response.setMsg("Failed");
           response.setMsg(e.getMessage());
           response.setSuccess(false);
           return response;
		}
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = ("/sessionTokenValidation/{sessionToken}"))
//	public Response sessionTokenValidator(@PathVariable("sessionToken") String sessionToken,HttpServletRequest httpServletRequest)
//	{
//		Response response=new Response();
//		try
//		{
//			AuthUtil auth=new AuthUtil();
//			boolean isAuthenticated = auth.isValidToken(httpServletRequest.getHeader("X-Lenskart-Email"),sessionToken);
//			if(!isAuthenticated)
//			{
//				response.setResult(false);
//		        response.setMsg("failure");
//		        response.setSuccess(false);
//		        return response;
//			}
//			response.setResult(null);
//			response.setMsg("successfull");
//			response.setSuccess(true);
//			return response;
//		}
//		catch (Exception e) {
//			response.setResult(null);
//	        response.setMsg(e.getMessage());
//	        response.setSuccess(false);
//	        return response;
//		}
//		
//		
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = ("/movieList/{keyword}"))
	public Response getMovieList(@PathVariable("keyword") String keyword,HttpServletRequest httpServletRequest)
	{
		Response response=null;
		try
		{
			AuthUtil auth=new AuthUtil();
			boolean isAuthenticated = auth.isValidToken(httpServletRequest.getHeader("X-Review-Movie-Email"),httpServletRequest.getHeader("X-Review-Movie-Session-Token"));
			response=new Response();
			response.setResult(reviewMovieService.getMovieList(keyword,httpServletRequest));
			response.setMsg("successfull");
			response.setSuccess(true);
			return response;
		}
		catch (Exception e) {
		   response.setResult(null);
		   if(e.getMessage()==null)
			   response.setMsg("Failed");
           response.setMsg(e.getMessage());
           response.setSuccess(false);
           return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = ("/bookmarkMovie/{bookmark}"))
	public Response getMovieList(@RequestBody Bookmark bookmark,HttpServletRequest httpServletRequest)
	{
		Response response=null;
		try
		{
			AuthUtil auth=new AuthUtil();
			boolean isAuthenticated = auth.isValidToken(httpServletRequest.getHeader("X-Review-Movie-Email"),httpServletRequest.getHeader("X-Review-Movie-Session-Token"));
			response=new Response();
			response.setResult(reviewMovieService.BookmarkMovie(bookmark,httpServletRequest));
			response.setMsg("successfull");
			response.setSuccess(true);
			return response;
		}
		catch (Exception e) {
		   response.setResult(null);
		   if(e.getMessage()==null)
			   response.setMsg("Failed");
           response.setMsg(e.getMessage());
           response.setSuccess(false);
           return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = ("/bookmarkMovie/{userId}"))
	public Response getMovieList(@PathVariable int userId,HttpServletRequest httpServletRequest)
	{
		Response response=null;
		try
		{
			AuthUtil auth=new AuthUtil();
			boolean isAuthenticated = auth.isValidToken(httpServletRequest.getHeader("X-Review-Movie-Email"),httpServletRequest.getHeader("X-Review-Movie-Session-Token"));
			response=new Response();
			response.setResult(reviewMovieService.getBookmarkedMovie(userId,httpServletRequest));
			response.setMsg("successfull");
			response.setSuccess(true);
			return response;
		}
		catch (Exception e) {
		   response.setResult(null);
		   if(e.getMessage()==null)
			   response.setMsg("Failed");
           response.setMsg(e.getMessage());
           response.setSuccess(false);
           return response;
		}
	}
}
