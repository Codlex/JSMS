package com.codlex.student.profesorskooko;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Klasa ObsluzivacSlikom je servis koji profesorskom programu omogucuje sliku ekrana studenta svaki put kada on to zatrazi.
 * Osim toga klasa ima sporednu ulogu da kroz istu konekciju profesor moze da izbaci studenta sa ispita.
 * 
 * @author Dejan Pekter RN 13/11
 */
public class ObsluzivacSlikom implements Runnable {
	
	/** Robot klasu koristim za dobijanje screenshot */
	Robot robot;
	
	// Pravim objekat ekran tipa Rectangle dimenzija ekrana koji cu koristiti za uzimanje slike ekrana
	Dimension dimenzijeEkrana = Toolkit.getDefaultToolkit().getScreenSize();
	Rectangle ekran = new Rectangle(dimenzijeEkrana.width, dimenzijeEkrana.height);
	
	ServerSocket dolaznaKonekcija;
	

	public ObsluzivacSlikom(){
		
		try {

			robot = new Robot();
			dolaznaKonekcija = new ServerSocket(2020);
			
		} catch (AWTException e) {
			// Exception u slucaju kreiranja robota
			JOptionPane.showMessageDialog(Konektovano.getInstance(), "Greska: Nije moguce napraviti robota.");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			// Exception u slucaju otvaranja dolazne konekcije
			JOptionPane.showMessageDialog(Konektovano.getInstance(), "Greska: Nije moguce slusati na portu 2020, proverite da niste vec otvorili program");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 *  Slika ekran studenta
	 *
	 * @return slika ekrana studenta
	 */
	private BufferedImage uslikajEkran(){
		
		return robot.createScreenCapture(ekran);
	}
	/**
	 * Telo servisa obsluzivaca slikama
	 */
	@Override
	public void run() {


		Socket trenutnaKonekcija = null;
		
		while(true){
			try {
				//prihvatanje konekcije profesora
				trenutnaKonekcija = dolaznaKonekcija.accept();
				
				// prepakujem sock u stream iz koga mogu da ucitavam i upisujem string bez transformacija
				InputStreamReader izvor = (new InputStreamReader(trenutnaKonekcija.getInputStream()));
				BufferedReader stringUcitavanje = new BufferedReader(izvor);
				String poruka = stringUcitavanje.readLine();

				// Proveravam da li profesorski program zeli da me izbaci sa ispita
				if(poruka.equals("izbaceni ste")){
					// Gasim prijavljivaca na profesorski program
					PrikupljanjePodataka.getInstance().getPrijavljivac().setRadi(false);
					// Sklanjam prozor koji pokazuje da je konekcija aktivna
					Konektovano.getInstance().setVisible(false);
					//Prikazujem prozor za izbacene studente
					IzbaceniStudent izbaceniStudent = new IzbaceniStudent();
					return; //ne zelim da nastavim da primam konekcije ukoliko je student izbacen
				}
				
				ImageIO.write(uslikajEkran(), "jpg", trenutnaKonekcija.getOutputStream());
				
				//Zatvaram socket ako je otvoren
				if(trenutnaKonekcija!=null)
					trenutnaKonekcija.close();

			} catch (Exception e) {
				// Ukoliko nije moguce citati/pisati na socket nema smisla da o tome obavestavam korisnika jednostavno izbacujem fatalnu gresku
				JOptionPane.showMessageDialog(Konektovano.getInstance(), "Doslo je do fatalne greske u mreznoj komunikaciji.");
			}
			
		}
		
	}

}
