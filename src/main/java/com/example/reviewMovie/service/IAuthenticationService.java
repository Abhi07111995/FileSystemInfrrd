package com.example.reviewMovie.service;

import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

	boolean requestValidator(HttpServletRequest request);
}
