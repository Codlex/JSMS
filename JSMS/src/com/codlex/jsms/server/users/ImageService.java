package com.codlex.jsms.server.users;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ImageService {
	private TreeMap<String, Image> screenShots;
	private static ImageService instanca;
	public static Image notAvailableImage;
	static {
		try {
			ImageIO.read(ImageService.class.getResource("/resources/no-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
			Image image = screenShots.get(token);
			if( image == null) {
				image = notAvailableImage;
			}
			return image;
		}
		return null;
	}

}
