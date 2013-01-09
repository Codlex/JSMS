package com.codlex.jsms.networking.messages.objects;

import java.awt.Image;
import java.io.Serializable;




@Deprecated
public class SlikaSaIdentifikacijom implements Serializable {

	private static final long serialVersionUID = 1L;
	private Image slika;
	private String token;
	
	public SlikaSaIdentifikacijom(String token, Image slika) {
		this.slika = slika;
		this.token = token;
	}
	public Image getSlika() {
		return slika;
	}
	public String getToken() {
		return token;
	}
	
	
	

}
