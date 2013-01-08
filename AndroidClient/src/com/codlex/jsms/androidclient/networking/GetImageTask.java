package com.codlex.jsms.androidclient.networking;

import java.io.InputStream;

import com.codlex.jsms.androidclient.model.BaseFriend;
import com.codlex.jsms.client.model.Friend;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetImageTask extends AsyncTask<Friend, Void, Bitmap>{

	@Override
	protected Bitmap doInBackground(Friend... params) {
		Friend activeFriend = params[0];
		Bitmap newImage = null;
		
		// ucitavanje slike sa strima
		Message response = CentralizedServerNIC.getNICService().getScreen(activeFriend.getUsername());
		newImage = BitmapFactory.decodeStream( (InputStream) response.getMsgObject());
		
		return newImage;
	}
	

}
