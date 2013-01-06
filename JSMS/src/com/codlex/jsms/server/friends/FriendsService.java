package com.codlex.jsms.server.friends;

import java.util.Collection;
import java.util.Stack;
import java.util.TreeMap;

import com.codlex.jsms.networking.User;

public class FriendsService {
	
	private static FriendsService instanca;
	
	private TreeMap<String, Stack<String> > friends;
	public static synchronized FriendsService getFriendsService() {
		if( instanca == null ){
			instanca = new FriendsService();
		}
		return instanca;
	}
	
	private FriendsService() {
		friends = new TreeMap<String, Stack<String> >();		
	}
	
	public synchronized void  addFriend(String toUsername, String newFriend) {
		Stack<String> userFriends = friends.get(toUsername);
		if(userFriends == null) {
			userFriends = new Stack<String>();
			friends.put(toUsername, userFriends);
		}
		
		//Dont make duplicates
		for(String friend : userFriends) {
			if(friend.equals(newFriend)) {
				return;
			}
		}
		userFriends.add(newFriend);
	}
	
	public Collection<String> getFriends(String username) {
		return friends.get(username);
	}
	

}
