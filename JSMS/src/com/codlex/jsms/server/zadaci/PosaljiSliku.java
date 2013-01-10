package com.codlex.jsms.server.zadaci;

import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;
import static com.codlex.jsms.server.users.ServisSlika.getServisSlika;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaNeuspesna;
import com.codlex.jsms.networking.messages.PorukaKorisnikNePostoji;
import com.codlex.jsms.networking.messages.PorukaUspeha;
import com.codlex.jsms.networking.messages.objects.ZahtevZaIdentifikacijom;
import com.codlex.jsms.utils.PisacKompresovaneSlike;

public class PosaljiSliku implements Runnable {
	
	private Socket socket;
	private static int brojTredova = 0;
	public PosaljiSliku(Socket socket) {
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
			System.out.println("[POSILJALAC_SLIKE::SERVER] Socket adresa: "
					+ socket.getLocalAddress());

			// podesavamo timeout ako klijent pukne
			socket.setSoTimeout(4000);
			System.out.println("[POSILJALAC_SLIKE::SERVER] Socket timeout: "
					+ socket.getSoTimeout());

			System.out
					.println("[POSILJALAC_SLIKE::SERVER] Konekcija uspostavljena");

			// podesavamo ulaz za ucitavanje poruke
			ulaz = new ObjectInputStream(socket.getInputStream());
			System.out.println("[POSILJALAC_SLIKE::SERVER] Ucitavamo poruku");
			Poruka poruka = (Poruka) ulaz.readObject();
			System.out.println("[POSILJALAC_SLIKE::SERVER] Poruka ucitana");
			// pravimo izlaz za nas odgovor na zahtev
			izlaz = new ObjectOutputStream(socket.getOutputStream());
			// iz poruke uzimamo zahtev
			ZahtevZaIdentifikacijom zahtev = (ZahtevZaIdentifikacijom) poruka
					.getObjekatPoruke();
			// proveravamo da li je korisnik ulogovan
			if (getKorisnickiServis().getKorisnikPoTokenu(zahtev.getToken()) != null) {
				Korisnik korisnik = getKorisnickiServis()
						.getKorisnikPoKorisnickomImenu(
								zahtev.getZahtevanoKorisnickoIme());
				if (korisnik == null) {
					System.out.println("[POSILJALAC_SLIKE::SERVER] Korisnik "
							+ zahtev.getZahtevanoKorisnickoIme()
							+ " nije pronadjen!");
					izlaz.writeObject(new PorukaKorisnikNePostoji());
				}
				// ukoliko je korisnik pronadjen saljemo korisniku odgovor
				// da se spremi za ucitavanje slike
				izlaz.writeObject(new PorukaUspeha());
				System.out
						.println("[POSILJALAC_SLIKE::SERVER] Poruka o uspesnom zahtevu poslata!");
				System.out.println("[POSILJALAC_SLIKE::SERVER] Saljem sliku!");
				// uzimam sliku trazenog korisnika
				Image slika = getServisSlika().getSlika(korisnik.getToken());
				// saljem sliku korisniku
				PisacKompresovaneSlike.jpgIspisiUSlabomKvalitetu(
						(BufferedImage) slika, socket.getOutputStream());
				System.out.println("[POSILJALAC_SLIKE::SERVER] Slika poslata!");
				// saljem signal da je slika poslata tako sto zatvaram
				// socket sa moje strane

			} else {
				izlaz.writeObject(new PorukaAutorizacijaNeuspesna());
				System.out
						.println("[POSILJALAC_SLIKE::SERVER] Neuspelo slanje");
			}
		} catch (SocketTimeoutException e) {
			System.out.println("[PRIMAC_SLIKA::SERVER] Predugo citanje/pisanje, otkazujem!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[POSILJALAC_SLIKE::SERVER] Izuzetak 1");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("[POSILJALAC_SLIKE::SERVER] Izuzetak 2");

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println("[POSILJALAC_SLIKE::SERVER] Izuzetak 3");

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

}
