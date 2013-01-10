package com.codlex.jsms.server.users;

import java.awt.Image;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 * Singlton servis za rad sa slikama u sistemu.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public class ServisSlika {
	
	private TreeMap<String, Image> skrinsot;
	private static ServisSlika instanca;
	public static Image nedostupnaSlika;
	
	// staticka inicijalizacija
	static {
		try {
			// ucitavam sliku koja ce biti prikazana kada prava slika nije dostupna
			nedostupnaSlika = ImageIO.read(ServisSlika.class.getResource("/resources/no-image.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private ServisSlika() {
		skrinsot = new TreeMap<String, Image>();
	}
	
	public static synchronized ServisSlika getServisSlika() {
		if(instanca == null) {
			instanca = new ServisSlika();
		}
		return instanca;
	}
	
	public void setSlika(String token, Image slika) {
		// podesavamo sliku za usera sa datim tokenom
		skrinsot.put(token, slika);
	}
	
	public Image getSlika(String token) {
		// proveravamo da li postoji slika za dati token
		if(skrinsot.containsKey(token)) {
			// uzimamo sliku za dati token
			Image slika = skrinsot.get(token);
			// dupla provera da li je slika sa datim tokenom podesena
			if( slika == null) {
				// ukoliko nije vracamo da je slika nedostupna
				slika = nedostupnaSlika;
			}
			return slika;
		}
		return nedostupnaSlika;
	}

}
