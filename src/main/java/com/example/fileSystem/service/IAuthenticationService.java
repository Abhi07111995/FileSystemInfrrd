package com.example.fileSystem.service;

import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationService {

	boolean requestValidator(HttpServletRequest request);
}
