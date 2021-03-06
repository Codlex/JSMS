package com.codlex.jsms.server.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.codlex.jsms.networking.User;

public class UserService {
	private static UserService instanca;
	private List<User> registredUsers;
	public static synchronized UserService getUserService()  {
		if(instanca == null) {
			instanca = new UserService();
		}
		return instanca;
	}
	private UserService() {
		registredUsers = new ArrayList<User>();
		
	}
	
	public String login(User user) {
		for(User registredUser : registredUsers) {
			if(registredUser.getUsername().equals(user.getUsername()) &&
			   registredUser.getPassword().equals(user.getPassword())){
				//Postoji!
				//Dodeli novi token
				String token = UUID.randomUUID().toString();
				registredUser.setToken(token);
				return token;
			}
		}
		
		return null;
	}
	
	public String createUser(User user) {
		for(User registredUser : registredUsers) {
			if(registredUser.getUsername().equals(user.getUsername())){
				return null;
			}
		}
		String token = UUID.randomUUID().toString();
		user.setToken(token);
		registredUsers.add(user);
		return token;	
	}
	
	public User getUserByName(String username) {
		for(User registredUser : registredUsers) {
			if(registredUser.getUsername().equals(username)){
				return registredUser;
			}
		}
		return null;
	}
	
	public User getUserByToken(String token) {
		for(User registredUser : registredUsers) {
			if(registredUser.getToken().equals(token)){
				return registredUser;
			}
		}
		return null;
	}
	

}
