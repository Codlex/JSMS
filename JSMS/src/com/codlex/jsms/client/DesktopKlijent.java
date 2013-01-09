package com.codlex.jsms.client;

import com.codlex.jsms.client.gui.ProzorZaPrijavljivanje;

/**
 * Glavna klasa koja pokrece prozor za prijavljivanje na sistem za desktop klijent-a.
 * 
 * @author Dejan Pekter <dpekter@raf.edu.rs>
 *
 */
public class DesktopKlijent {
	public static void main(String[] args) {
		// malo debug-a
		System.out.println("Inicijalizacija klijenta");
		System.out.println("*****************************");
		System.out.println("Prikazivanje ekrana za login");
		// prikazivanje programa za prijavljivanje
		ProzorZaPrijavljivanje prijavljivanje = new ProzorZaPrijavljivanje();
		prijavljivanje.setVisible(true);		
	}		
}