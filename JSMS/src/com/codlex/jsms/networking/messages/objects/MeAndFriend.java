package com.codlex.jsms.networking.messages.objects;

import java.io.Serializable;

public class MeAndFriend implements Serializable {
	
	private String token;
	private String friend;
	
	public MeAndFriend(String token, String friend) {
		this.token = token;
		this.friend = friend;
	}

	public String getToken() {
		return token;
	}

	public String getFriend() {
		return friend;
	}
	
	

}
