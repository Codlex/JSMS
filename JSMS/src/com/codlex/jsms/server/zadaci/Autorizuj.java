package com.codlex.jsms.server.zadaci;

import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaNeuspesna;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaUspesna;
import com.codlex.jsms.networking.messages.PorukaOAutorizacijiKorisnikPostoji;

public class Autorizuj implements Runnable {
	private Socket socket;
	private static int brojTredova = 0;

	public Autorizuj(Socket socket) {
		uvecajBrojAktivnihTredova();
		this.socket = socket;
	}

	public static synchronized int getBrojAktivnihTredova() {
		return brojTredova;
	}
	
	public static synchronized void uvecajBrojAktivnihTredova() {
		brojTredova++;
	}
	
	public static synchronized void umanjiBrojAktivnihTredova() {
		brojTredova--;
	}

	@Override
	public void run() {
		ObjectInputStream ulaz = null;
		ObjectOutputStream izlaz = null;
		try {
			socket.setSoTimeout(2000);
			System.out
					.println("[AUTORIZACIJA::SERVER] Konekcija uspostavljena");
			// pravimo object input stream kako bi procitali poruku od korisnika
			ulaz = new ObjectInputStream(socket.getInputStream());
			System.out.println("[AUTORIZACIJA::SERVER] Ucitavanje poruke");
			Poruka poruka = (Poruka) ulaz.readObject();
			System.out.println("[AUTORIZACIJA::SERVER] Poruka primljena");
			Poruka odgovor = obradiPoruku(poruka);
			System.out
					.println("[AUTORIZACIJA::SERVER] Poruka obradjena, saljem odgovor");
			izlaz = new ObjectOutputStream(socket.getOutputStream());
			izlaz.writeObject(odgovor);
			System.out.println("[AUTORIZACIJA::SERVER] Odgovor poslat");
		} catch (SocketTimeoutException e) {
			System.out
					.println("[PRIMAC_SLIKA::SERVER] Predugo citanje/pisanje, otkazujem!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ulaz.close();
				izlaz.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			umanjiBrojAktivnihTredova();
		}
	}

	private static Poruka obradiPoruku(Poruka poruka) {
		Korisnik korisnik = null;
		String token = null;
		switch (poruka.getKodPoruke()) {
		case AUTH:
			// primljen je zahtev za autorizaciju
			System.out
					.println("[AUTORIZACIJA::SERVER] Primljena poruka sa AUTH kodom");
			korisnik = (Korisnik) poruka.getObjekatPoruke();
			// trazim od sistema da prijavi korisnika
			token = getKorisnickiServis().ulogujSe(korisnik);
			// saljem odgovor token ako je uspesno ili neuspesnu poruku
			if (token == null) {
				return new PorukaAutorizacijaNeuspesna();
			}
			return new PorukaAutorizacijaUspesna(token);
		case REGISTER:
			// primljen je zahtev za registraciju korisnika
			System.out
					.println("[AUTORIZACIJA::SERVER] Primljena poruka sa REGISTER kodom");
			korisnik = (Korisnik) poruka.getObjekatPoruke();
			// registrovanje korisnika na sistem
			token = getKorisnickiServis().registrujKorisnika(korisnik);
			// ukoliko je token null korisnik nije uspesno registrovan na sistem
			if (token == null) {
				return new PorukaOAutorizacijiKorisnikPostoji();
			}
			return new PorukaAutorizacijaUspesna(token);
		default:
			return null;
		}

	}

}
