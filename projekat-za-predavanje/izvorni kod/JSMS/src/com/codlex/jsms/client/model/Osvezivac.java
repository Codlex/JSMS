package com.codlex.jsms.client.model;

/**
 * Tred koji osvezava trenutno selektovanog korisnika u modelu prijatelja trenutno ulogovanog korisnika.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public class Osvezivac implements Runnable {
	private boolean zaustavljeno;
	/**
	 * Ova metoda zaustavlja slanje ekrana trenutnog klijenta.
	 */
	public void zaustavljeno() {
		zaustavljeno = true;
	}
	private int vremeCekanja = 400;
	@Override
	public void run() {
		// dokle god nije zaustavljno osvezavamo ekran trenutno selektovanog korisnika
		while(!zaustavljeno) {
			
			if( !(ModelListePrijateljaImplementacija.getPane().getSelectedComponent() instanceof TabbedPanePrijatelj) ) {
				// ukoliko nije selektovan korisnicki pane ne radimo nista
				System.out.println("[Osvezivac] Panel bez slike");
				try {
					Thread.sleep(vremeCekanja * 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			
			// algoritam za cekanje
			System.out.println("[Osvezivac] Zadatak za slanje slike startovan, trenutno je ukljuceno " + OsvezivacZadatak.getBrojAktivnihTredova() + " zadataka");
			if(OsvezivacZadatak.getBrojAktivnihTredova() > 3) {
				
				try {
					// Algoritam za balansiranje vremena cekanja
					Thread.sleep(vremeCekanja);
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Thread zadatak = new Thread(new OsvezivacZadatak());
			zadatak.start();
			
			try {
				// Algoritam za balansiranje vremena cekanja
				Thread.sleep(vremeCekanja);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
		}
	}

}
