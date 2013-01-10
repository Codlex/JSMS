package com.codlex.jsms.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.server.zadaci.Prijateljizuj;

/**
 * Server koji prihvata slike ekrana korisnika i osvezava svoju bazu.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */
public class ServerPrijateljstva implements Server {
	private static final int port = 1331;

	@SuppressWarnings("resource")
	@Override
	public void run() {
		ServerSocket server = null;
		// pravimo server socket
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int socketNum = 0;

		System.out.println("[PRIJATELJSTVO::SERVER] Server startovan na portu "
				+ port);
		while (true) {
			System.out
					.println("[PRIJATELJSTVO::SERVER] Broj aktivnih tredova: "
							+ Prijateljizuj.getBrojAktivnihTredova());
			// primamo konekciju
			try {
				System.out.println("[PRIJATELJSTVO::SERVER] Konekcija br: "
						+ socketNum++);
				Socket socket = server.accept();
				if(Prijateljizuj.getBrojAktivnihTredova() < 20) {
					Thread autorizacija = new Thread(new Prijateljizuj(socket));
					autorizacija.start();
				}
				else {
					System.out.println("[PRIJATELJSTVO::SERVER] konekcija odbacena - previse konekcija");
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
