package com.codlex.jsms.networking.messages.objects;

import java.io.Serializable;


/**
 * Koristi se za potrebe identifikacije pri zahtevu korisnickog ekrana.
 * 
 * @author Vukoje
 *
 */

public class ZahtevZaIdentifikacijom implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private String zahtevanoKorisnickoIme;
	
	public ZahtevZaIdentifikacijom(String token, String zahtevanoKorisnickoIme) {
		this.token = token;
		this.zahtevanoKorisnickoIme = zahtevanoKorisnickoIme;
	}
	public String getToken() {
		return token;
	}
	public String getZahtevanoKorisnickoIme() {
		return zahtevanoKorisnickoIme;
	}
	
	

}
