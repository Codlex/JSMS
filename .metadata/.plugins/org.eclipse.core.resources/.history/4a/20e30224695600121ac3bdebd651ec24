package com.codlex.profesor.profesorskooko;

/**
 * 
 * Ulazna klasa za pokretanje profesorskog programa.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */
public class ProfesorskoOko {
	public static void main(String[] args) {
		
		
		//pokrenem tread koji prima konekcije
		//ove konekcije sluze prijavljivanju korisnika na sistem
		Thread menadzerKonekcijama = new Thread(new PrihvatilacKonekcija());
		menadzerKonekcijama.start();
		
		
		//podignem korisnicko okuzenje
		ProzorPrekoCelogEkrana glavniProzor = new ProzorPrekoCelogEkrana("Profesorsko oko");
		glavniProzor.setVisible(true);
		

	}

}
