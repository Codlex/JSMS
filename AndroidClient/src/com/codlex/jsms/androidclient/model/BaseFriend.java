package com.codlex.jsms.androidclient.model;

import java.awt.Image;

import android.graphics.Bitmap;

import com.codlex.jsms.client.model.Friend;

public class BaseFriend implements Friend {
	String username;
	boolean isOnline;
	Bitmap image;
	
	public BaseFriend(String username) {
		this.username = username;
		isOnline = false;
		image = null;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return isOnline;
	}

	// ovo je drugacije
	public Bitmap getScreenBitmap() {
		return image;
	}
	
	public void setBitmap(Bitmap image){
		this.image = image;
	}

	@Override
	public Image getScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}
