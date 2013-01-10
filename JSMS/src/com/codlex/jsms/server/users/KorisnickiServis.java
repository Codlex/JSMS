package com.codlex.jsms.server.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.codlex.jsms.networking.Korisnik;

/**
 * Singlton servis za rad sa korisnicima u sistemu.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public class KorisnickiServis {
	
	private static KorisnickiServis instanca;
	private List<Korisnik> registrovaniKorisnici;
	
	public static synchronized KorisnickiServis getKorisnickiServis()  {
		if(instanca == null) {
			instanca = new KorisnickiServis();
		}
		return instanca;
	}
	
	private KorisnickiServis() {
		registrovaniKorisnici = new ArrayList<Korisnik>();
	}
	
	public synchronized String ulogujSe(Korisnik korisnik) {
		for(Korisnik registrovanKorisnik : registrovaniKorisnici) {
			if(registrovanKorisnik.getKorisnickoIme().equals(korisnik.getKorisnickoIme()) &&
			   registrovanKorisnik.getLozinka().equals(korisnik.getLozinka())){
				//Postoji!
				//Dodeli novi token
				String token = UUID.randomUUID().toString();
				registrovanKorisnik.setToken(token);
				// vracamo korisniku token, kad dodju ljudi da ima
				return token;
			}
		}
		return null;
	}
	
	public synchronized String registrujKorisnika(Korisnik korisnik) {
		for(Korisnik registrovanKorisnik : registrovaniKorisnici) {
			// ukoliko korisnik vec postoji vracamo null kao indikaciju da nije moguce napraviti korisnika sa tim username
			if(registrovanKorisnik.getKorisnickoIme().equals(korisnik.getKorisnickoIme())){
				return null;
			}
		}
		
		// ukoliko je prosao testove gore tada se korisniku dodeljuje novi token i automatski se prijavljuje na sistem
		String token = UUID.randomUUID().toString();
		korisnik.setToken(token);
		registrovaniKorisnici.add(korisnik);
		// vracamo korisniku token, kad dodju ljudi da ima
		return token;	
	}
	
	public synchronized Korisnik getKorisnikPoKorisnickomImenu(String korisnickoIme) {
		for(Korisnik registrovaniKorisnik : registrovaniKorisnici) {
			if(registrovaniKorisnik.getKorisnickoIme().equals(korisnickoIme)){
				return registrovaniKorisnik;
			}
		}
		return null;
	}
	
	public synchronized Korisnik getKorisnikPoTokenu(String token) {
		for(Korisnik registrovanKorisnik : registrovaniKorisnici) {
			if(registrovanKorisnik.getToken().equals(token)){
				return registrovanKorisnik;
			}
		}
		return null;
	}
	

}
