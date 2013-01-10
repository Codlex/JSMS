package com.codlex.jsms.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.server.zadaci.PosaljiSliku;

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
		// pravimo server socket
		try {
			server = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int socketNum = 0;

		System.out.println("[POSILJALAC_SLIKE::SERVER] Server startovan na portu " + port);
		while(true) {
			System.out.println("[POSILJALAC_SLIKE::SERVER] Broj aktivnih tredova: " + PosaljiSliku.getBrojAktivnihTredova());
			// primamo konekciju
			try {
				System.out.println("[POSILJALAC_SLIKE::SERVER] Konekcija br: " + socketNum++);
				Socket socket = server.accept();
				if(PosaljiSliku.getBrojAktivnihTredova() < 20) {
					Thread autorizacija = new Thread(new PosaljiSliku(socket));
					autorizacija.start();
				}
				else {
					System.out.println("[POSILJALAC_SLIKE::SERVER] konekcija odbacena - previse konekcija");
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
