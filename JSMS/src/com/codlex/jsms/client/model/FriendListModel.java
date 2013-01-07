package com.codlex.jsms.client.model;

import java.awt.Image;
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
 * @author Dejan Pekter <deximat@gmail.com>
 * 
 */
public interface FriendListModel {
	public Collection<Friend> getFriends();
	public Friend getFriend(String userName);
	public boolean addFriend(String username);
	// Konstruktor koji uzima korisnicko ime onoga ko koristi model
}
