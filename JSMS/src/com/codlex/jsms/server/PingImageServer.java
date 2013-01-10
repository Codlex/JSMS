package com.codlex.jsms.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.server.zadaci.PrimiSliku;

/**
 * Server koji prihvata slike ekrana korisnika i osvezava svoju bazu. 
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */
public class PingImageServer implements Server{
	
	private static final int port = 6767;
	
	@SuppressWarnings("resource")
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
				System.out.println("[PRIMAC_SLIKA::SERVER] Server startovan na portu " + port);
				while(true) {
					System.out.println("[PRIMAC_SLIKA::SERVER] Broj aktivnih tredova: " + PrimiSliku.getBrojAktivnihTredova());
					// primamo konekciju
					try {
						System.out.println("[PRIMAC_SLIKA::SERVER] Konekcija br: " + socketNum++);
						Socket socket = server.accept();
						if(PrimiSliku.getBrojAktivnihTredova() < 20) {
							Thread autorizacija = new Thread(new PrimiSliku(socket));
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
