package com.codlex.jsms.androidclient.networking;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.getNICService;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;

import android.os.AsyncTask;

public class CreateAccountTask extends AsyncTask<String, Void, Message>{

	@Override
	protected Message doInBackground(String... params) {
		String username = params[0];
		String password = params[1];
		String email 	= params[2];
		
		User tmpUser = new BaseUser(username,password,email);
		Message response = getNICService().createAccount(tmpUser);
		
		return response;
	}

}
