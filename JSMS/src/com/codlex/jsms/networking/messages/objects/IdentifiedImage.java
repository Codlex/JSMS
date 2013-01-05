package com.codlex.jsms.networking.messages.objects;

import java.awt.Image;
import java.io.Serializable;

public class IdentifiedImage implements Serializable {
	private Image image;
	private String token;
	public IdentifiedImage(String token, Image image) {
		this.image = image;
		this.token = token;
	}
	public Image getImage() {
		return image;
	}
	public String getToken() {
		return token;
	}
	
	
	

}
