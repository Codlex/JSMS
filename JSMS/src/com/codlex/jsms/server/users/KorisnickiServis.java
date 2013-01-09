package com.codlex.jsms.server.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.codlex.jsms.networking.User;

/**
 * Singlton servis za rad sa korisnicima u sistemu.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public class KorisnickiServis {
	
	private static KorisnickiServis instanca;
	private List<User> registrovaniKorisnici;
	
	public static synchronized KorisnickiServis getKorisnickiServis()  {
		if(instanca == null) {
			instanca = new KorisnickiServis();
		}
		return instanca;
	}
	
	private KorisnickiServis() {
		registrovaniKorisnici = new ArrayList<User>();
	}
	
	public String ulogujSe(User korisnik) {
		for(User registrovanKorisnik : registrovaniKorisnici) {
			if(registrovanKorisnik.getUsername().equals(korisnik.getUsername()) &&
			   registrovanKorisnik.getPassword().equals(korisnik.getPassword())){
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
	
	public String registrujKorisnika(User korisnik) {
		for(User registrovanKorisnik : registrovaniKorisnici) {
			// ukoliko korisnik vec postoji vracamo null kao indikaciju da nije moguce napraviti korisnika sa tim username
			if(registrovanKorisnik.getUsername().equals(korisnik.getUsername())){
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
	
	public User getKorisnikPoKorisnickomImenu(String korisnickoIme) {
		for(User registrovaniKorisnik : registrovaniKorisnici) {
			if(registrovaniKorisnik.getUsername().equals(korisnickoIme)){
				return registrovaniKorisnik;
			}
		}
		return null;
	}
	
	public User getKorisnikPoTokenu(String token) {
		for(User registrovanKorisnik : registrovaniKorisnici) {
			if(registrovanKorisnik.getToken().equals(token)){
				return registrovanKorisnik;
			}
		}
		return null;
	}
	

}
