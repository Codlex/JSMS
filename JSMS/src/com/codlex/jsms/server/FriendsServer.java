package com.codlex.jsms.server;

import static com.codlex.jsms.server.friends.ServisPrijateljstva.getServisPrijateljstva;
import static com.codlex.jsms.server.users.KorisnickiServis.getKorisnickiServis;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Stack;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.messages.PorukaSaPrijateljima;
import com.codlex.jsms.networking.messages.PorukaUspeha;
import com.codlex.jsms.networking.messages.PorukaKorisnikNePostoji;
import com.codlex.jsms.networking.messages.objects.JaIPrijatelj;

/**
 * Server koji prihvata slike ekrana korisnika i osvezava svoju bazu. 
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 */
public class FriendsServer implements Server{
	private static final int port = 1331;
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		try {
			// pravimo novi server socket
			ServerSocket server = new ServerSocket(port);
			System.out.println("[PRIJATELJSTVO::SERVER] Server je startovan na portu " + port);
			while(true) {
				// cekam na konekcije
				Socket socket = server.accept();
				System.out.println("[PRIJATELJSTVO::SERVER] Konekcija uspostavljena");
				// pravim object input stream da primim poruku
				ObjectInputStream ulaz = new ObjectInputStream(socket.getInputStream());
				System.out.println("[PRIJATELJSTVO::SERVER] Ucitavam poruku");
				Poruka poruka = (Poruka) ulaz.readObject();
				System.out.println("[PRIJATELJSTVO::SERVER] Poruka primljena");
				Poruka odgovor = obradiPoruku(poruka);
				System.out.println("[PRIJATELJSTVO::SERVER] Poruka obradjena, saljem odgovor");
				ObjectOutputStream izlaz = new ObjectOutputStream(socket.getOutputStream());
				izlaz.writeObject(odgovor);				
				System.out.println("[PRIJATELJSTVO::SERVER] Odgovor poslat");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Poruka obradiPoruku(Poruka poruka) {
		switch (poruka.getKodPoruke()) {
			case GET_FRIENDS: 
				// poruka koju smo primili zahteva listu svih prijatelja identifikovanog korisnika
				System.out.println("[PRIJATELJSTVO::SERVER] Dobijen GET_FRIENDS kod");
				// token korisnika koji trazi uslugu
				String token = (String) poruka.getObjekatPoruke();
				// uzimamo korisnika po njegovom tokenu
				Korisnik korisnik = getKorisnickiServis().getKorisnikPoTokenu(token);
				// ukoliko nije null to znaci da je token ok
				if(korisnik == null) {
					return new PorukaKorisnikNePostoji();
				}
				// uzimamo njegove prijatelje
				Collection<String> prijatelji = getServisPrijateljstva().getPrijatelji(korisnik.getKorisnickoIme());
				// ukoliko nema prijatelje saljemo mu praznu listu
				if( prijatelji == null || prijatelji.isEmpty() ) {
					return new PorukaSaPrijateljima(new Stack<String>());
				}
				return new PorukaSaPrijateljima(prijatelji);				
			case ADD_FRIEND:
				// poruka koju smo primili zahteva dodavanje novog prijatelja identifikovanom korisniku
				System.out.println("[PRIJATELJSTVO::SERVER] Dobijen ADD_FRIEND kod");
				JaIPrijatelj jaIPrijatelj = (JaIPrijatelj) poruka.getObjekatPoruke();
				// iz poruke izvlacimo informacije potrebne za obradu poruke
				Korisnik ja = getKorisnickiServis().getKorisnikPoTokenu(jaIPrijatelj.getToken());
				Korisnik ono = getKorisnickiServis().getKorisnikPoKorisnickomImenu(jaIPrijatelj.getPrijatelj());
				// proveravamo da li zeljeni korisnici postoje u sistemu
				if(ja == null || ono == null ) {
					return new PorukaKorisnikNePostoji();
				}
				// ukoliko smo sve gore prosli, mozemo da dodamo vezu u graf prijateljstva
				getServisPrijateljstva().dodajPrijatelja(ja.getKorisnickoIme(), ono.getKorisnickoIme());
				System.out.println("[PRIJATELJSTVO::SERVER] " + ja.getKorisnickoIme() + " se upoznao sa " + ono.getKorisnickoIme());
				// saljemo poruku o uspehu
				return new PorukaUspeha();				
			default:
				return null;
		}
		
		
	}
	
	
}
