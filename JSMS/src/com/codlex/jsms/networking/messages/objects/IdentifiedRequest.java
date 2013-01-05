package com.codlex.jsms.networking.messages.objects;

import java.io.Serializable;

public class IdentifiedRequest implements Serializable {
	String tokenSignature;
	String requestedUsername;
	public IdentifiedRequest(String token, String username) {
		this.tokenSignature = token;
		this.requestedUsername = username;
	}
	public String getTokenSignature() {
		return tokenSignature;
	}
	public String getRequestedUsername() {
		return requestedUsername;
	}
	
	

}
