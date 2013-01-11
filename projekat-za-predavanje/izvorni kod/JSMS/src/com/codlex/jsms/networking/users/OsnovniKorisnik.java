package com.codlex.jsms.networking.users;

import com.codlex.jsms.networking.Korisnik;

public class OsnovniKorisnik implements Korisnik {

	private static final long serialVersionUID = 1L;

	private String korisnickoIme;
	private String email;
	private String token;
	private String lozinka;
	
	public OsnovniKorisnik(String korisnickoIme, String lozinka) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
	}
	
	public OsnovniKorisnik(String korisnickoIme, String lozinka, String email) {
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	


}
