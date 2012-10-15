package com.codlex.student.profesorskooko;

import javax.swing.JDialog;

/**
 * Ova klasa predstavlja samo osnovnu klasu iz koje se pokrece servis za obsuzivanje profesorskog programa 
 * sa slikama studentskog racunara i iscrtavanje grafickog interfejs-a za student-a.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */
public class StudentskiProgram {

	/**
	 * 
	 * Klasa za pokretanje programa
	 * 
	 */
	
	public static void main(String[] args) {
		
		//Pravi novi tred koji prihvata konekcije profesorskog programa kao zahteve slike ekrana
		Thread obsluzivacZahteva = new Thread(new ObsluzivacSlikom());
		obsluzivacZahteva.start();
				
		//Iscrtava graficki interfejs za studenta
		// ne kreiram objekat zbog koriscenja singletona
		PrikupljanjePodataka.getInstance().setVisible(true);	
	}
}
