package com.codlex.jsms.server.zadaci;

import static com.codlex.jsms.server.users.ServisSlika.getServisSlika;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.Poruka;

public class PrimiSliku implements Runnable {
	private Socket socket;
	private static volatile int brojTredova = 0;

	public PrimiSliku(Socket socket) {
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
		try {
			socket.setSoTimeout(4000);
			System.out
					.println("[PRIMAC_SLIKA::SERVER] Konekcija uspostavljena");
			// pravimo input object stream kako bi mogli ucitati poruku
			ulaz = new ObjectInputStream(socket.getInputStream());
			;
			System.out.println("[PRIMAC_SLIKA::SERVER] Ucitavamo poruku");
			Poruka poruka = (Poruka) ulaz.readObject();
			System.out
					.println("[PRIMAC_SLIKA::SERVER] Poruka primljena - identifikacija");
			System.out.println("[PRIMAC_SLIKA::SERVER] Ucitavam sliku...");
			// ucitavamo sliku od korisnika koji se upravo identifikovao sa
			// zacetnom porukom
			Image slika = ImageIO.read(socket.getInputStream());
			System.out.println("[PRIMAC_SLIKA::SERVER] Slika procitana...");
			String token = (String) poruka.getObjekatPoruke();
			// ubacujemo sliku u bazu
			getServisSlika().setSlika(token, slika);
		} catch (SocketTimeoutException e) {
			System.out.println("[PRIMAC_SLIKA::SERVER] Predugo citanje/pisanje, otkazujem!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[PRIMAC_SLIKA::SERVER] Izuzetak uhvlajskjdfc.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// na kraju zatvarmo socket
			if (socket != null) {
				try {
					if(ulaz != null)
						ulaz.close();;
					if(socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				umanjiBrojAktivnihTredova();
			}
		}

	}

}
