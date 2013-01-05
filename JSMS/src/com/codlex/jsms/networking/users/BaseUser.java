package com.codlex.jsms.networking.users;

import com.codlex.jsms.networking.User;

public class BaseUser implements User {

	private static final long serialVersionUID = 1L;

	private String username;
	private String email;
	private String token;
	private String password;
	
	public BaseUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public BaseUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	


}