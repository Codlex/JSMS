package com.codlex.jsms.androidclient.networking;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.getNICService;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;
import android.os.AsyncTask;

public class LoginTask extends AsyncTask<String, Void, Message>{


	@Override
	protected Message doInBackground(String... params) {
		
		// pokupimo prosledjen user i pw
		String username = params[0];
		String password = params[1];
		
		User tmpUser = new BaseUser(username,password);
		Message response = getNICService().logIn(tmpUser);
		return response;
	}

}
