package com.codlex.jsms.networking;

/**
 * NIC predstavlja izlaznu tacku na mrezu i podrzava vrlo genericnu funkciju 
 * slanja poruke od strane korisnika koji je potpisan tokenom, do korisnika 
 * po korisnickom imenu. Osim toga ovaj interfejs omogucava logovanje korisnika
 * koji ne poseduje token.
 * 
 * @author deximat
 *
 */
public interface NIC {
	public Poruka sendMessage(Poruka message);
	/**
	 * Metoda vraca poruku koja sadrzi kod koji govori da li je uspesno logovanje
	 * i ukoliko jeste tada se u getMessageObject te poruke nalazi string sa 
	 * tokenom za upravo logovanog korisnika
	 * 
	 * @param authInfo - poruka koja sadrzi informacije potrebne za logovanje
	 * 
	 */
	public Poruka logIn(User authInfo);
	public void logOut();
	public Poruka createAccount(User user);
	public Poruka addFriend(String username);
	public Poruka getFriends();
	public boolean isLoggedIn();
	public User getLoggedUser();
	public Poruka getScreen(String username);
	public Poruka pingScreen();

}
