package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaNeuspesna;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaUspesna;
import com.codlex.jsms.networking.messages.PorukaOAutorizacijiKorisnikPostoji;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

/**
 * AuthServer je napravljen da prima konekcije od neprijavljenih 
 * korisnika na sistem i da im vraca poruku u obliku uspeha ili 
 * neuspeha, sa tokenom odnosno bez njega respektivno. 
 * 
 * @author Dejan Pekter RN 13/11 <deximat@gmail.com>
 *
 */
public class AuthServer implements Server{
	private static final int port = 1337;
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		dodajProbnogKorisnika();
		// pravimo server socket
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("[AUTORIZACIJA::SERVER] Server startovan na portu " + port);
		while(true) {
			try {
					// primamo konekciju
					Socket socket = server.accept();
					System.out.println("[AUTORIZACIJA::SERVER] Konekcija uspostavljena");
					// pravimo object input stream kako bi procitali poruku od korisnika
					ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
					System.out.println("[AUTORIZACIJA::SERVER] Ucitavanje poruke");
					Poruka poruka = (Poruka) ulaz.readObject();
					System.out.println("[AUTORIZACIJA::SERVER] Poruka primljena");
					Poruka odgovor = obradiPoruku(poruka);
					System.out.println("[AUTORIZACIJA::SERVER] Poruka obradjena, saljem odgovor");
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(odgovor);				
					System.out.println("[AUTORIZACIJA::SERVER] Odgovor poslat");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Poruka obradiPoruku(Poruka poruka) {
		Korisnik korisnik = null;
		String token = null;
		switch (poruka.getKodPoruke()) {
		case AUTH: 
			// primljen je zahtev za autorizaciju
			System.out.println("[AUTORIZACIJA::SERVER] Primljena poruka sa AUTH kodom");
			korisnik = (Korisnik) poruka.getObjekatPoruke();
			// trazim od sistema da prijavi korisnika
			token = getKorisnickiServis().ulogujSe(korisnik);
			// saljem odgovor token ako je uspesno ili neuspesnu poruku
			if(token == null) {
				return new PorukaAutorizacijaNeuspesna();				
			}
			return new PorukaAutorizacijaUspesna(token);
		case REGISTER:
			// primljen je zahtev za registraciju korisnika
			System.out.println("[AUTORIZACIJA::SERVER] Primljena poruka sa REGISTER kodom");
			korisnik = (Korisnik) poruka.getObjekatPoruke();
			// registrovanje korisnika na sistem
			token = getKorisnickiServis().registrujKorisnika(korisnik);
			// ukoliko je token null korisnik nije uspesno registrovan na sistem
			if(token == null) {
				return new PorukaOAutorizacijiKorisnikPostoji();				
			}
			return new PorukaAutorizacijaUspesna(token);
		default:
			return null;
		}
		
	}
	
	private void dodajProbnogKorisnika() {
		//u svrhu testiranja
		Korisnik korisnik = new OsnovniKorisnik("demo", "demo");
		if(getKorisnickiServis().registrujKorisnika(korisnik) != null) {
			System.out.println("Korisnik ubacen u sistem vestacki");
		}
		else {
			System.out.println("Neuspesno dodavanje demo naloga!");
		}

	}
	
}
