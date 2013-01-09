package com.codlex.jsms.client.model;

import java.awt.Image;

/**
 * Interfejs koja predstavlja prijatelja, dajuci mogucnost da se dobije korisnicko ime, slika ekrana tog prijatelja i da li je online.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */

public interface Prijatelj {
	public String getKorisnickoIme();
	public Image getSlikaEkrana();
	public boolean jeOnlajn();
}
