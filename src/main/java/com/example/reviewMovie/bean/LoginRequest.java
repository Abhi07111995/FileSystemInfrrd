package com.example.reviewMovie.bean;


public class LoginRequest {
	
		public LoginRequest(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}
		
		public LoginRequest() {
		}
		public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		private String emailId;
		private String password;
	
}
