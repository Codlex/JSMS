package com.codlex.jsms.networking;

import java.io.Serializable;

/**
 * Korisnik predstavlja korisnika i na klijentskoj i na serverskoj strani.
 * Preko ovog objekta se vrsi registracija i prijavljivanje na sistem. 
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public interface Korisnik extends Serializable{
	public String getKorisnickoIme();
	public String getLozinka();
	public String getEmail();
	
	/**
	 * Token je tajni dugacki string koji identifikuje korisnika, 
	 * korisnik ga moze dobiti samo od servera.
	 * 
	 * Sa tokenom korisnik dobija pristup svim servisima servera.
	 * 
	 */
	
	public String getToken();
	public void setToken(String token);
}
