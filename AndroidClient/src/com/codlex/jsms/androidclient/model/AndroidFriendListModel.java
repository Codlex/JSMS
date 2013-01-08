package com.codlex.jsms.androidclient.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import com.codlex.jsms.androidclient.networking.GetFriendsTask;
import com.codlex.jsms.androidclient.networking.AddFriendTask;
import com.codlex.jsms.client.model.Friend;
import com.codlex.jsms.client.model.FriendListModel;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

public class AndroidFriendListModel implements FriendListModel{
	private Collection<Friend> friends;
	private String userToken;
	private BaseFriend activeFriend;
	// static activeUser;
	
	public AndroidFriendListModel(String token) {
		this.userToken = token;
		this.friends = new ArrayList<Friend>();
		this. activeFriend = null;
		// uzima prijatelje
		GetFriendsTask getFriendsTask = new GetFriendsTask();
		getFriendsTask.execute();
		try {
			this.friends = getFriendsTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Collection<Friend> getFriends() {
		// TODO Auto-generated method stub
		return friends;
	}
	
	public Collection<String> getUsernamesOfFriends(){
		Collection<String> usernamesOfFriends = new ArrayList<String>();
		for(Friend friend:friends){
			usernamesOfFriends.add(friend.getUsername());
		}
		return usernamesOfFriends;
	}
	
	@Override
	public Friend getFriend(String userName) {
		// TODO Auto-generated method stub
		
		for(Friend friend: friends){
			if(userName.equals(friend.getUsername()))
				return friend;
		}
	
		return null;
	}

	@Override
	public boolean addFriend(String username) {
		
		// task za dodavanje prijatelja
		AddFriendTask addFriendTask = new AddFriendTask();
		addFriendTask.execute(username);
		
		Message response = null;
		
		try {
			response = addFriendTask.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(response.getMsgCode().equals(MSGCode.SUCCESS)){
			friends.add(new BaseFriend(username));
			return true;
		}
		return false;
		// TODO Auto-generated method stub
		
	}
	
	public void setActiveFriend(BaseFriend friend){
		activeFriend = friend;
	}
	
	public BaseFriend getActiveFriend(){
		return activeFriend;
	}

}
