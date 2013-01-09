package com.codlex.jsms.networking;

/**
 * NIC predstavlja izlaznu tacku na mrezu i podrzava vrlo genericnu funkciju 
 * slanja poruke od strane korisnika koji je potpisan tokenom, do korisnika 
 * po korisnickom imenu. Osim toga ovaj interfejs omogucava logovanje korisnika
 * koji ne poseduje token.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public interface NIC {
	/**
	 * @deprecated
	 */
	public Poruka posaljiPoruku(Poruka poruka);
	
	/**
	 * Metoda vraca poruku koja sadrzi kod koji govori da li je uspesno logovanje
	 * i ukoliko jeste tada se u objektu te poruke nalazi string sa 
	 * tokenom za upravo logovanog korisnika
	 * 
	 * @param korisnik - poruka koja sadrzi informacije potrebne za logovanje
	 * 
	 */
	public Poruka prijaviSe(Korisnik korisnik);
	
	public void odjaviSe();
	
	/**
	 * Metoda vraca poruku koja sadrzi kod koji govori da li je uspesno pravljenje naloga
	 * i ukoliko jeste tada se u objektu te poruke nalazi string sa 
	 * tokenom za upravo napravljenog korisnika
	 * 
	 * @param korisnik - poruka koja sadrzi informacije potrebne za kreiranje naloga
	 * 
	 */
	public Poruka napraviNalog(Korisnik korisnik);

	public Poruka dodajPrijatelja(String korisnickoIme);
	
	/**
	 * Vraca poruku sa prijateljima od trenutno logovanog korisnika
	 * Potrebno je da je korisnik ulogovan 
	 */
	public Poruka getPrijatelji();
	
	public boolean jeUlogovan();
	
	public Korisnik getTrenutnoUlogovanKorisnik();
	/***
	 * Poruku sa slikom ekrana trenutno prijavljenog korisnika.
	 * 
	 * @param username - korisnik ciji ekran zelimo
	 * 
	 */
	public Poruka getEkran(String username);
	
	/**
	 * Salje sliku ekrana trenutno prijavljenog korisnika serveru.
	 * 
	 */
	public Poruka posaljiEkran();

}
