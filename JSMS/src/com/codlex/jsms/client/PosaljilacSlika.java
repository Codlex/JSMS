package com.codlex.jsms.client;

import com.codlex.jsms.client.model.OsvezivacZadatak;

/**
 * Ova klasa predstavlja tred koji ce u realnom vremenu osvezavati sliku
 * ulogovanog korisnika na serveru.
 * 
 * @author Dejan Pekter <dpekter@raf.edu.rs>
 * 
 */
public class PosaljilacSlika implements Runnable {
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
		while (!zaustavljeno) {

			// algoritam za cekanje
			System.out
					.println("[Osvezivac] Zadatak za slanje slike startovan, trenutno je ukljuceno "
							+ OsvezivacZadatak.getBrojAktivnihTredova()
							+ " zadataka");
			if (PosaljilacSlikaZadatak.getBrojAktivnihTredova() > 1) {

				try {
					// Algoritam za balansiranje vremena cekanja
					Thread.sleep(vremeCekanja);
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Thread zadatak = new Thread(new PosaljilacSlikaZadatak());
			zadatak.start();
		}

	}
}
