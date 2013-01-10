package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.ServisSlika.getServisSlika;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.Poruka;

/**
 * Server koji prihvata slike ekrana korisnika i osvezava svoju bazu. 
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */
public class PingImageServer implements Server{
	
	private static final int port = 6767;
	
	@SuppressWarnings("resource")
	public void run() {
			// otvaramo server socket
			ServerSocket server = null;
			ObjectInputStream ulaz = null;
			Socket socket = null;
			try {
				server = new ServerSocket(port);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			int socketNum = 0;

			System.out.println("[PRIMAC_SLIKA::SERVER] Server startovan na portu " + port);
			// server osluskuje na korisnicke zahteve za slanje slika
			while(true) {
				try {
					System.out.println("[PRIMAC_SLIKA::SERVER] Konekcija br: " + socketNum++);
					// cekamo konekciju
					socket = server.accept();
					// podesavamo timeout ukoliko korisnik pukne
					socket.setSoTimeout(2000);
					System.out.println("[PRIMAC_SLIKA::SERVER] Konekcija uspostavljena");
					if(socket.isClosed()) {
						// korisnik puko
						continue;
					}
					// pravimo input object stream kako bi mogli ucitati poruku
					ulaz = new ObjectInputStream(socket.getInputStream());;
					System.out.println("[PRIMAC_SLIKA::SERVER] Ucitavamo poruku");
					Poruka poruka = (Poruka) ulaz.readObject();
					System.out.println("[PRIMAC_SLIKA::SERVER] Poruka primljena - identifikacija");
					System.out.println("[PRIMAC_SLIKA::SERVER] Ucitavam sliku...");
					// ucitavamo sliku od korisnika koji se upravo identifikovao sa zacetnom porukom
					Image slika = ImageIO.read(socket.getInputStream());
					System.out.println("[PRIMAC_SLIKA::SERVER] Slika procitana...");
					String token = (String) poruka.getObjekatPoruke();
					// ubacujemo sliku u bazu
					getServisSlika().setSlika(token, slika);				
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[PRIMAC_SLIKA::SERVER] Izuzetak uhvlajskjdfc.");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					// na kraju zatvarmo socket
					if(socket != null) {
						try {
							ulaz.close();
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}				
			}
	}
}
