package com.codlex.jsms.networking.NICS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.NIC;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.PorukaNeuspeha;
import com.codlex.jsms.networking.messages.PorukaSaZahtevomZaPrijatelje;
import com.codlex.jsms.networking.messages.PorukaUspeha;
import com.codlex.jsms.networking.messages.PorukaZaAutorizaciju;
import com.codlex.jsms.networking.messages.PorukaZaDodavanjePrijatelja;
import com.codlex.jsms.networking.messages.PorukaZaPrimanjeSlike;
import com.codlex.jsms.networking.messages.PorukaZaRegistracijuKorisnika;
import com.codlex.jsms.networking.messages.PorukaZaSlanjeSlike;
import com.codlex.jsms.networking.messages.objects.ZahtevZaIdentifikacijom;


/**
 * Ovaj singlton implementira NIC koji obavlja svu komunikaciju sa serverom
 * za klijenta i omogucava enkapsulaciju korisnickih informacija.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public class CentralizovaniNIC implements NIC {

	/**
	 * Adrese servera
	 * Adrese su razdvojene zbog moguceg distribuiranog sistema.
	 */
	
	private static final String adresaServeraZaAutorizaciju = "jsms.codlex.com";
	private static final int portServeraZaAutorizaciju = 1337;
	
	private static final String adresaServeraPrijateljstva = "jsms.codlex.com";
	private static final int portServeraPrijateljstva = 1331;
	
	private static final String adresaServeraPosiljaocaSlika = "jsms.codlex.com";
	private static final int portServeraPosiljaocaSlika = 6768;
	
	private static final String adresaServeraPrimaocaSlika = "jsms.codlex.com";
    private static final int portServeraPrimaocaSlika = 6767;
	
	// Prijavljen korisnikov kontekst
	Korisnik korisnik;
	
	private static NIC instance;
	
	public static synchronized NIC getNICService() {
		if( instance == null ){
			instance = new CentralizovaniNIC();
		}
		return instance;
	}
	
	private CentralizovaniNIC() {}
	
	
	
	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka napraviNalog(Korisnik korisnik) {
		try {
			// otvaramo socket ka serveru
		    Socket socket = new Socket(adresaServeraZaAutorizaciju, portServeraZaAutorizaciju);
		    System.out.println("Konekcija uspostavljena sa serverom za autorizaciju");
		    // pravimo object output stream kako bi serveru poslali poruku
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo novu poruku za registraciju korisnika
		    Poruka poruka = new PorukaZaRegistracijuKorisnika(korisnik);
		    // saljemo zahtev za registraciju korisnika
		    izlaz.writeObject(poruka);
		    // ocekujemo odgovor od servera
		    ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
			Poruka odgovor = (Poruka) ulaz.readObject();
			System.out.println("Server je odgovorio sa kodom");
			System.out.println(odgovor.getKodPoruke());
			System.out.println("Token:" + odgovor.getObjekatPoruke());
			// Podesavam kontekst korisnika
			korisnik.setToken((String) odgovor.getObjekatPoruke());
			this.korisnik = korisnik;
			// zatvaram socket
			izlaz.close();
		    ulaz.close();
		    // vracam odgovor na dalju obradu od strane klijenta
			return odgovor;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new PorukaNeuspeha();
	}

	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka prijaviSe(Korisnik korisnik) {
		try {
			// otvaramo konekciju ka serveru
		    Socket socket = new Socket(adresaServeraZaAutorizaciju, portServeraZaAutorizaciju);
		    System.out.println("Konekcija uspostavljena sa serverom!");
		    // otvaramo ObjectOutputStream da bi bili u mogucnosti da saljemo poruku serveru
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo poruku za autorizaciju
		    Poruka poruka = new PorukaZaAutorizaciju(korisnik);
		    // saljemo poruku serveru
		    izlaz.writeObject(poruka);
		    System.out.println("Korisnik " + korisnik.getKorisnickoIme() + " je poslao " + 
		    		           "zahtev da bude autorizovan sa lozinkom " + 
		    		           korisnik.getLozinka());
		    // pravimo input stream kako bi ucitali odgovor servera
		    ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
			Poruka odgovor = (Poruka) ulaz.readObject();
			System.out.println("Server je odgovorio sa kodom");
			System.out.println(odgovor.getKodPoruke());
			System.out.println("Token:" + odgovor.getObjekatPoruke());
			
			// Podesavanje konteksta korisnika
			korisnik.setToken((String) odgovor.getObjekatPoruke());
			this.korisnik = korisnik;
			// zatvaramo konekcije
			izlaz.close();
		    ulaz.close();
		    
		    // vracamo odgovor klijentu na dalju obradu
			return odgovor;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new PorukaNeuspeha();
	}
	
	@Deprecated
	@Override
	public Poruka posaljiPoruku(Poruka message)  {
		System.out.println("Not implemented");
		return new PorukaNeuspeha();
	}
	
	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka dodajPrijatelja(String korisnickoIme) {
		try {
			// otvaramo konekciju sa serverom
		    Socket socket = new Socket(adresaServeraPrijateljstva, portServeraPrijateljstva);
		    System.out.println("Uspostavljena konekcija sa serverom prijateljstva!");
		    // pravimo object output kako bi mogli da saljemo poruke serveru
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo poruku za dodavanje prijatelja
		    Poruka poruka = new PorukaZaDodavanjePrijatelja(this.korisnik.getToken(), korisnickoIme);
		    izlaz.writeObject(poruka);
		    System.out.println("Poslat zahtev za dodavanje prijatelja");
		    // pravimo object input stream kako bi ucitali odgovor servera
		    ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
			Poruka odgovor = (Poruka) ulaz.readObject();
			System.out.println("Server je odgovorio sa kodom");
			System.out.println(odgovor.getKodPoruke());
			System.out.println("Token:" + odgovor.getObjekatPoruke());
			// gasimo konekcije
			izlaz.close();
		    ulaz.close();
		      
			return odgovor;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new PorukaNeuspeha();
	}
	
	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka getPrijatelji() {
		try {
			// otvaramo konekciju sa serverom
		    Socket socket = new Socket(adresaServeraPrijateljstva, portServeraPrijateljstva);
		    System.out.println("Uspostavljena konekcija sa serverom prijateljstva!");
		    // pravimo object output stream kako bi mogli da saljemo poruke serveru
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo poruku za dobijanje svih prijatelja prijavljenog korisnika
		    Poruka poruka = new PorukaSaZahtevomZaPrijatelje(this.korisnik.getToken());
		    izlaz.writeObject(poruka);
		    System.out.println("Zahtev za listu prijatelja poslat!");
		    // pravimo object input stream kako bi procitali odgovor servera na nas zahtev
		    ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
			Poruka odgovor = (Poruka) ulaz.readObject();
			System.out.println("Server je odgovorio sa kodom");
			System.out.println(odgovor.getKodPoruke());
			System.out.println("Token:" + odgovor.getObjekatPoruke());
			// gasimo konekcije
			izlaz.close();
		    ulaz.close();
		  
			return odgovor;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return new PorukaNeuspeha();
	}
	
	
	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka getEkran(String korisnickoIme) {
		Socket socket = null;
		try {
			// otvaramo konekciju sa serverom
		    socket = new Socket(adresaServeraPosiljaocaSlika, portServeraPosiljaocaSlika);
		    
		    System.out.println("Konekcija uspostavljena sa serverom posiljaoca slika!");
		    // pravimo object output stream kako bi poslali poruku serveru
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo poruku za uzimanje ekrana datog prijatelja
		    ZahtevZaIdentifikacijom zahtev = new ZahtevZaIdentifikacijom(this.korisnik.getToken(), korisnickoIme);
		    Poruka poruka = new PorukaZaPrimanjeSlike(zahtev);
		    izlaz.writeObject(poruka);
		    System.out.println("Zahtev za ekran poslat");
		    // pravimo object input stream kako bi procitali odgovor servera
		    ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
			// primamo odogovor servera
		    Poruka odgovor = (Poruka) ulaz.readObject();
			System.out.println("Server je odgovorio sa kodom");
			System.out.println(odgovor.getKodPoruke());
			System.out.println("Token:" + odgovor.getObjekatPoruke());
			// ako je slika uspesno procitana onda saljemo klijentu poruku sa output streamom odakle moze da ucita sliku
			if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)) {
				PorukaUspeha message = (PorukaUspeha)odgovor;
				message.setObjekatPoruke(socket.getInputStream());
			}		      
			return odgovor;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		try {
			socket.getInputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new PorukaNeuspeha();
	}
	
	@SuppressWarnings("resource")
	@Override
	public synchronized Poruka posaljiEkran() {
		try {
			// otvaramo konekciju sa serverom
		    Socket socket = new Socket(adresaServeraPrimaocaSlika, portServeraPrimaocaSlika);
		    System.out.println("Konekcija uspostavljena sa serverom primaocom slika!");
		    // pravimo object output stream da bi mogli da mu posaljemo poruku
		    ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
		    // pravimo poruku koja priprema server da primi sliku
		    Poruka poruka = new PorukaZaSlanjeSlike(this.korisnik.getToken());
		    izlaz.writeObject(poruka);
		    System.out.println("Poruka za pripremu servera da primi sliku poslata");
		    // genericnu poruku uspeha saljemo klijentu sa output streamom-om na koji treba da drmne sliku
		    PorukaUspeha odgovor = new PorukaUspeha();
			odgovor.setObjekatPoruke(socket.getOutputStream());  
			return odgovor;
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return new PorukaNeuspeha();
	}
	@Override
	public synchronized Korisnik getTrenutnoUlogovanKorisnik() {
		return korisnik;
	}
	
	@Override
	public synchronized boolean jeUlogovan() {
		return korisnik != null;
	}
	
	@Override
	public synchronized void odjaviSe() {
		korisnik = null;
	}
	
}
