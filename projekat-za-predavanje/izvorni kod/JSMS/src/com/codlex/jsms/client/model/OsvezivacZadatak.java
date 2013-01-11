package com.codlex.jsms.client.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

public class OsvezivacZadatak implements Runnable {
	private static int brojTredova = 0;
	

	public OsvezivacZadatak() {
		uvecajBrojAktivnihTredova();
	}

	public static synchronized int getBrojAktivnihTredova() {
		return brojTredova;
	}
	
	public static synchronized void uvecajBrojAktivnihTredova() {
		brojTredova++;
	}
	
	public static synchronized void umanjiBrojAktivnihTredova() {
		brojTredova--;
	}
	
	@Override
	public void run() {
		// proveravamo da li je selektovan korisnicki pane ili podrazumevani

		// uzimamo selektovanog prijatelja
		TabbedPanePrijatelj selektovanPrijatelj = (TabbedPanePrijatelj) ModelListePrijateljaImplementacija.getPane().getSelectedComponent();
		String username = selektovanPrijatelj.getKorisnickoIme();
		// od servera trazimo sliku njegovog ekrana
		Poruka poruka = CentralizovaniNIC.getNICService().getEkran(username);
		BufferedImage slika = null;
		// ukoliko je server odlucio da nam posalje njegovu sliku, dobicemo success
		if(poruka.getKodPoruke().equals(MSGCode.SUCCESS)) {
			// u poruci nam je prosledjen inputstream na kome se nalazi slika
			InputStream ulaz = (InputStream) poruka.getObjekatPoruke();
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
			//selektovanPrijatelj.osvezi();
		}
		else {
			System.out.println("Slika je null - boom");
		}
		
		umanjiBrojAktivnihTredova();

	}

}
