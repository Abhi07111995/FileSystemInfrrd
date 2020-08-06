package com.example.reviewMovie.bean;


import java.util.List;


import lombok.Data;

@Data
public class LoginResponse {
	
	String message;
	int flag;
	String loginToken;
	
}
