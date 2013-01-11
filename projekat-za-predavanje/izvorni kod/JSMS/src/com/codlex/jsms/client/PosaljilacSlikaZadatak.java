package com.codlex.jsms.client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;
import com.codlex.jsms.utils.PisacKompresovaneSlike;

public class PosaljilacSlikaZadatak implements Runnable {
	private static int brojTredova = 0;
	

	public PosaljilacSlikaZadatak() {
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

		// pravimo robota kako bi preko njega uzeli sliku ekrana na kome se
		// izvrsava aplikacija
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		// izracunavamo dimenzije ekrana na osnovu informacija iz sistema
		Dimension dimenzijeEkrana = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle povrsinaEkrana = new Rectangle(dimenzijeEkrana.width,
				dimenzijeEkrana.height);
		System.out.println("Zapoceo sam slanje ekrana!");
		// uzimam sliku trenutnog ekrana
		BufferedImage slikaEkrana = robot.createScreenCapture(povrsinaEkrana);
		// obavesti server da mu saljemo sliku
		Poruka poruka = CentralizovaniNIC.getNICService().posaljiEkran();
		// mrezni deo sistema nam daje upravljanje mrezom kroz OutputStream
		OutputStream izlaz = (OutputStream) poruka.getObjekatPoruke();
		try {
			// saljemo kompresovanu slikuEkrana na izlaz
			PisacKompresovaneSlike
					.jpgIspisiUSlabomKvalitetu(slikaEkrana, izlaz);
			System.out.println("Ekran poslat!");
		} finally {
			try {
				// zatvaramo konekciju
				izlaz.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			umanjiBrojAktivnihTredova();
		}

	}

}
