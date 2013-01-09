package com.codlex.jsms.client.model;

import java.util.Collection;

/**
 * 
 * Interfejs FriendListModel predstavlja glavnu kontaktnu tacku izmedju klijenta 
 * i servera.
 * U modelu se automatski osvezavaju podaci koji se nalaze na serveru i korisnik
 * modela ne mora da razmislja o mrezi, vec samo zahteva informaciju koja mu je 
 * potrebna. Posto je model zajednicki za sve vrste klijenata on je izdvojen u 
 * zaseban projekat i kompajlira se u jar koji koristi konkretan klijent.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 * 
 */

public interface ModelLIstePrijatelja {
	/**
	 * Vraca kolekciju prijatelja trenutno ulogovanog korisnika.
	 * 
	 */
	public Collection<Prijatelj> getPrijatelji();
	/**
	 * Vraca korisnikovog prijatelja po njegovom korisnickom imenu.
	 * 
	 */
	public Prijatelj getPrijatelj(String korisnickoIme);
	/**
	 * Dodaje kao  prijatelja korisnika sa datim korisnickim imenom korisniku koji je trenutno ulogovan.
	 * 
	 */
	public boolean dodajPrijatelja(String korisnickoIme);
	
}
