package com.codlex.jsms.androidclient.model;


import java.util.ArrayList;
import java.util.Collection;
import com.codlex.jsms.client.model.Friend;
import com.codlex.jsms.client.model.FriendListModel;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

public class AndroidFriendListModel implements FriendListModel{
	Collection<String> usernames;
	Collection<Friend> friends;
	String userToken;
	
	public AndroidFriendListModel(String token) {
		this.userToken = token;
		this.usernames = new ArrayList<String>();
		this.friends = new ArrayList<Friend>();
		
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Friend> getFriends() {
		// TODO Auto-generated method stub
		Message response = CentralizedServerNIC.getNICService().getFriends();
		
		
		if(response.getMsgCode() != MSGCode.USER_DOESNT_EXIST){
		 usernames = (Collection<String>) response.getMsgObject();
	
		 for(String username: usernames){
			 friends.add(new BaseFriend(username) );
		 }
		 
		 return friends;
		}//Friend[] friends = (Friend[]) response.getMsgObject();
		else
		 return null;
		
	}
	
	public Collection<String> getUsernamesOfFriends(){
		return usernames;
	}
	
	@Override
	public Friend getFriend(String userName) {
		// TODO Auto-generated method stub
		
	//	Message response = CentralizedServerNIC.getNICService().getFriend();
		
		
		return null;
	}

	@Override
	public void addFriend(String username) {
		
		Message response = CentralizedServerNIC.getNICService().addFriend(username);
		if(response.getMsgCode().equals(MSGCode.SUCCESS)){
			friends.add((Friend) response.getMsgObject());
		}
		// TODO Auto-generated method stub
		
	}

}
