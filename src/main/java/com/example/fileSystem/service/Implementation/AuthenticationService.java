package com.example.fileSystem.service.Implementation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fileSystem.service.IAuthenticationService;
import com.example.fileSystem.util.HttpHeadersValidator;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	HttpHeadersValidator httpHeadersValidator; 

	@Override
	public boolean requestValidator(HttpServletRequest request) {
		return httpHeadersValidator.validateRequest(request);
	}
}
