package com.codlex.jsms.networking;

import java.io.Serializable;

public interface User extends Serializable{
	public String getUsername();
	public String getPassword();
	public String getEmail();
	public String getToken();
	public void setToken(String token);
}
