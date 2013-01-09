package com.codlex.jsms.server;

/**
 * Ulazna tacka za startovanje svih servera.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public class MainServer {
	public static void main(String[] args) {
		System.out.println("Codlex oko server je startovan!");
		
		System.out.println("***************************************");
		System.out.println("Inicijalizacija treda servera za autorizaciju");
		Server autorizacija = new AuthServer();
		Thread tredAutorizacija = new Thread(autorizacija);
		tredAutorizacija.start();
		System.out.println("Autorizacioni server je startovan");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Inicijalizacija servera za primanje slika");
		Server primacSlika = new PingImageServer();
		Thread tredPrimacaSlika = new Thread(primacSlika);
		tredPrimacaSlika.start();
		System.out.println("Server za primanje slika je startovan");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Inicijalizacija servera za slanje slika");
		Server posiljalacSlika = new GetImageServer();
		Thread tredPosiljaocaSlika = new Thread(posiljalacSlika);
		tredPosiljaocaSlika.start();
		System.out.println("Server za slanje slika je startovan");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Inicijalizacija servera za prijateljstva");
		Server prijateljstva = new FriendsServer();
		Thread tredPrijateljstva = new Thread(prijateljstva);
		tredPrijateljstva.start();
		System.out.println("Server za prijateljstva je startovan");
		System.out.println("***************************************");

		
		System.out.println("Serverski sistem je u potpunosti incicijalizovan!");
		
	}

}
