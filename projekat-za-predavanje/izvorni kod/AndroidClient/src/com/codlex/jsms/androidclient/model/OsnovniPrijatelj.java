package com.codlex.jsms.androidclient.model;

import java.awt.Image;

import android.graphics.Bitmap;

import com.codlex.jsms.client.model.Prijatelj;

/**
 * 
 * Ova klasa reprezentuje jednog od prijatelja koji se posmatraju i sve metode za rad 
 * sa prijateljem su sadrzane u ovoj klasi.
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */
public class OsnovniPrijatelj implements Prijatelj {
	private String korisnickoIme;
	private boolean jeOnlajn;
	private Bitmap slika;
	
	public OsnovniPrijatelj(String username) {
		// postavimo korisnicko ime
		// i postavimo jeOnlajn i sliku prijatelja na podrazumevanu vrednost
		this.korisnickoIme = username;
		jeOnlajn = false;
		slika = null;
	}
	
	@Override
	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	@Override
	public boolean jeOnlajn() {
		return jeOnlajn;
	}

	// slika za Android
	public Bitmap getEkranBitmap() {
		return slika;
	}
	
	public void setEkranBitmap(Bitmap slika){
		this.slika = slika;
	}
	
	@Override
	public Image getSlikaEkrana() {
		return null;
	}
}
