package com.codlex.jsms.networking;

public interface User {
	public String getUsername();
	public String getPassword();
	public String getEmail();
	public String getToken();
	public void setToken(String token);
}
