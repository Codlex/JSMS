package com.codlex.jsms.server.users;

import java.awt.Image;
import java.util.TreeMap;

public class ImageService {
	private TreeMap<String, Image> screenShots;
	private static ImageService instanca;
	
	private ImageService() {
		screenShots = new TreeMap<String, Image>();
	}
	
	public static synchronized ImageService getImageService() {
		if(instanca == null) {
			instanca = new ImageService();
		}
		return instanca;
	}
	
	public void setImage(String token, Image image) {
		screenShots.put(token, image);
	}
	
	public Image getImage(String token) {
		if(screenShots.containsKey(token)) {
			return screenShots.get(token);
		}
		return null;
	}

}
