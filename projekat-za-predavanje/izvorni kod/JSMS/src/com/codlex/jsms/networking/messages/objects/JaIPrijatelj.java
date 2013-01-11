package com.codlex.jsms.networking.messages.objects;

import java.io.Serializable;


/**
 * Klasa nosi informacije potrebne za dodavanje prijatelja.
 * 
 * @param token - Token korisnika koji zeli da doda prijatelja
 * @param prijatelj - Korisnicko ime korisnika koji postaje njegov prijatelj
 *
 */

public class JaIPrijatelj implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private String prijatelj;
	
	public JaIPrijatelj(String token, String prijatelj) {
		this.token = token;
		this.prijatelj = prijatelj;
	}

	public String getToken() {
		return token;
	}

	public String getPrijatelj() {
		return prijatelj;
	}
	
	

}
