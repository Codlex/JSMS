package com.codlex.jsms.androidclient.networking;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

import android.os.AsyncTask;

public class AddFriendTask extends AsyncTask<String, Void, Message>{

	@Override
	protected Message doInBackground(String... params) {
		
		String username = params[0];
		Message response = CentralizedServerNIC.getNICService().addFriend(username);
		return response;
	}

}
