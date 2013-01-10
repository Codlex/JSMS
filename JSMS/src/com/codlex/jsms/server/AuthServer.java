package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.users.OsnovniKorisnik;
import com.codlex.jsms.server.zadaci.Autorizuj;

/**
 * AuthServer je napravljen da prima konekcije od neprijavljenih korisnika na
 * sistem i da im vraca poruku u obliku uspeha ili neuspeha, sa tokenom odnosno
 * bez njega respektivno.
 * 
 * @author Dejan Pekter RN 13/11 <deximat@gmail.com>
 * 
 */
public class AuthServer implements Server {
	private static final int port = 1337;

	@SuppressWarnings("resource")
	@Override
	public void run() {
		dodajProbnogKorisnika();
		// pravimo server socket
		ServerSocket server = null;
		// pravimo server socket
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int socketNum = 0;

		System.out.println("[AUTORIZACIJA::SERVER] Server startovan na portu "
				+ port);
		while (true) {
			System.out.println("[AUTORIZACIJA::SERVER] Broj aktivnih tredova: "
					+ Autorizuj.getBrojAktivnihTredova());
			// primamo konekciju
			try {
				System.out.println("[AUTORIZACIJA::SERVER] Konekcija br: "
						+ socketNum++);
				Socket socket = server.accept();
				if(Autorizuj.getBrojAktivnihTredova() < 20) {
					Thread autorizacija = new Thread(new Autorizuj(socket));
					autorizacija.start();
				}
				else {
					System.out.println("[AUTORIZACIJA::SERVER] konekcija odbacena - previse konekcija");
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void dodajProbnogKorisnika() {
		// u svrhu testiranja
		Korisnik korisnik = new OsnovniKorisnik("demo", "demo");
		if (getKorisnickiServis().registrujKorisnika(korisnik) != null) {
			System.out.println("Korisnik ubacen u sistem vestacki");
		} else {
			System.out.println("Neuspesno dodavanje demo naloga!");
		}

	}

}
