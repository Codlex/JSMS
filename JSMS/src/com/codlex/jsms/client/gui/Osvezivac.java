package com.codlex.jsms.client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.codlex.jsms.client.model.ModelListePrijateljaImplementacija;
import com.codlex.jsms.client.model.TabbedPanePrijatelj;
import com.codlex.jsms.client.model.ModelListePrijateljaImplementacija.PodrazumevaniPanel;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;
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
	
	@Override
	public void run() {
		// dokle god nije zaustavljno osvezavamo ekran trenutno selektovanog korisnika
		while(!zaustavljeno) {
			// proveravamo da li je selektovan korisnicki pane ili podrazumevani
			if( !(ModelListePrijateljaImplementacija.getPane().getSelectedComponent() instanceof TabbedPanePrijatelj) ) {
				// ukoliko nije selektovan korisnicki pane ne radimo nista
				System.out.println("Panel bez slike");
				continue;
			}
			// uzimamo selektovanog prijatelja
			TabbedPanePrijatelj selektovanPrijatelj = (TabbedPanePrijatelj) ModelListePrijateljaImplementacija.getPane().getSelectedComponent();
			String username = selektovanPrijatelj.getKorisnickoIme();
			// od servera trazimo sliku njegovog ekrana
			Poruka poruka = CentralizedServerNIC.getNICService().getScreen(username);
			BufferedImage slika = null;
			// ukoliko je server odlucio da nam posalje njegovu sliku, dobicemo success
			if(poruka.getKodPoruke().equals(MSGCode.SUCCESS)) {
				// u poruci nam je prosledjen inputstream na kome se nalazi slika
				InputStream ulaz = (InputStream) poruka.getMsgObject();
				try {
					// ucitavanje slike korisnika
					slika = ImageIO.read(ulaz);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(slika != null) {
				System.out.println("Slika se osvezava");
				// podesavamo prijatelju novu sliku koju smo dobili sa servera
				selektovanPrijatelj.setEkran(slika);	
				// osvezavamo prikaz prijatelja
				selektovanPrijatelj.osvezi();
			}
			else {
				System.out.println("Slika je null - boom");
			}
			// cekamo odredjeno vreme, nakon cega ponovo zahtevamo sliku 
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
