package com.codlex.jsms.androidclient.networking;

import java.util.ArrayList;
import java.util.Collection;

import com.codlex.jsms.androidclient.model.AndroidFriendListModel;
import com.codlex.jsms.androidclient.model.BaseFriend;
import com.codlex.jsms.client.model.Friend;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

import android.os.AsyncTask;

public class GetFriendsTask extends AsyncTask<Void, Void, Collection<Friend>>{

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<Friend> doInBackground(Void... arg0) {
		Collection<String> usernames = new ArrayList<String>();
		Collection<Friend> friends = new ArrayList<Friend>();
		
		Message response = CentralizedServerNIC.getNICService().getFriends();
		
		if(response.getMsgCode() != MSGCode.USER_DOESNT_EXIST){
			 usernames = (Collection<String>) response.getMsgObject();
		
			 for(String username: usernames){
				 friends.add(new BaseFriend(username) );
			 }
			 return friends;
		}
		
		// TODO Auto-generated method stub
		return null;
	}


}
