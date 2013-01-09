package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.ServisSlika.getServisSlika;
import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.messages.PorukaAutorizacijaNeuspesna;
import com.codlex.jsms.networking.messages.PorukaUspeha;
import com.codlex.jsms.networking.messages.PorukaKorisnikNePostoji;
import com.codlex.jsms.networking.messages.objects.ZahtevZaIdentifikacijom;
import com.codlex.jsms.utils.PisacKompresovaneSlike;

/**
 * Server koji salje slike ekrana korisnika ciji se username prosledi u poruci. 
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */

public class GetImageServer implements Server {
	private static final int port = 6768;

	@SuppressWarnings("resource")
	@Override
	public void run() {
			// pravimo server socket
			ServerSocket server = null;
			try {
				server = new ServerSocket(port);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("[POSILJALAC_SLIKE::SERVER] Server je startovan na portu " + port);
			while(true) {
				try {
					// cekamo konekciju
					Socket socket = server.accept();
					// podesavamo timeout ako klijent pukne
					socket.setSoTimeout(500);
					System.out.println("[POSILJALAC_SLIKE::SERVER] Konekcija uspostavljena");
					if(socket.isClosed()) {
						// proveravamo da klijent nije pukao
						continue;
					}
					// podesavamo ulaz za ucitavanje poruke
					ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
					System.out.println("[POSILJALAC_SLIKE::SERVER] Ucitavamo poruku");
					Poruka poruka = (Poruka) ulaz.readObject();
					System.out.println("[POSILJALAC_SLIKE::SERVER] Poruka ucitana");
					// pravimo izlaz za nas odgovor na zahtev
					ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
					// iz poruke uzimamo zahtev
					ZahtevZaIdentifikacijom zahtev = (ZahtevZaIdentifikacijom) poruka.getObjekatPoruke();
					// proveravamo da li je korisnik ulogovan
					if( getKorisnickiServis().getKorisnikPoTokenu(zahtev.getToken()) != null ) {
						Korisnik korisnik = getKorisnickiServis().getKorisnikPoKorisnickomImenu(zahtev.getZahtevanoKorisnickoIme());
						if ( korisnik == null ) {
							System.out.println("[POSILJALAC_SLIKE::SERVER] Korisnik " + zahtev.getZahtevanoKorisnickoIme() + " nije pronadjen!");
							izlaz.writeObject( new PorukaKorisnikNePostoji() );
						}
						// ukoliko je korisnik pronadjen saljemo korisniku odgovor da se spremi za ucitavanje slike
						izlaz.writeObject(new PorukaUspeha());
						System.out.println("[POSILJALAC_SLIKE::SERVER] Poruka o uspesnom zahtevu poslata!");
						System.out.println("[POSILJALAC_SLIKE::SERVER] Saljem sliku!");
						// uzimam sliku trazenog korisnika
						Image slika = getServisSlika().getSlika(korisnik.getToken());
						// saljem sliku korisniku
		        		PisacKompresovaneSlike.jpgIspisiUSlabomKvalitetu((BufferedImage) slika, socket.getOutputStream());
		        		System.out.println("[POSILJALAC_SLIKE::SERVER] Slika poslata!");
		        		// saljem signal da je slika poslata tako sto zatvaram socket sa moje strane
						socket.close();
					}
					else {
						izlaz.writeObject( new PorukaAutorizacijaNeuspesna());
						System.out.println("[POSILJALAC_SLIKE::SERVER] Neuspelo slanje");			
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[POSILJALAC_SLIKE::SERVER] Izuzetak uhvlajsdc");
		
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			
	    }
	}
}
