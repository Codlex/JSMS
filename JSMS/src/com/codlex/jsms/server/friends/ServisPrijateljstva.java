package com.codlex.jsms.server.friends;

import java.util.Collection;
import java.util.Stack;
import java.util.TreeMap;
/**
 * Singlton servis za rad sa prijateljstvima u sistemu.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public class ServisPrijateljstva {
	
	private static ServisPrijateljstva instanca;
	
	private TreeMap<String, Collection<String> > prijatelji;
	
	public static synchronized ServisPrijateljstva getServisPrijateljstva() {
		if( instanca == null ){
			instanca = new ServisPrijateljstva();
		}
		return instanca;
	}
	
	private ServisPrijateljstva() {
		prijatelji = new TreeMap<String, Collection<String> >();		
	}
	
	public synchronized void  dodajPrijatelja(String zaKorisnickoIme, String noviPrijatelj) {
		// uzimamo kolekciju prijatelja za datog korisnika
		
		Collection<String> korisnikoviPrijatelji = prijatelji.get(zaKorisnickoIme);
		// ukoliko njegova kolekcija nije inicijalizovana, inicijalizujemo je
		if(korisnikoviPrijatelji == null) {
			korisnikoviPrijatelji = new Stack<String>();
			prijatelji.put(zaKorisnickoIme, korisnikoviPrijatelji);
		}		
		// Proveri da li su vec prijatelji
		for(String friend : korisnikoviPrijatelji) {
			if(friend.equals(noviPrijatelj)) {
				return;
			}
		}
		// dodaj ga kao prijatelja
		korisnikoviPrijatelji.add(noviPrijatelj);
	}
	
	public Collection<String> getPrijatelji(String korisnickoIme) {
		return prijatelji.get(korisnickoIme);
	}
	

}
