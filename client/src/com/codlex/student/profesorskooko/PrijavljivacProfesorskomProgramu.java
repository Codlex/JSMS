package com.codlex.student.profesorskooko;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * PrijavljivacProfesorskomProgramu javlja profesorskom programu da je konekcija i dalje ziva.
 * 
 * @author Dejan Pekter RN 13/11
 */
public class PrijavljivacProfesorskomProgramu implements Runnable {
	
	/** Ovu promenljivu koristim kao prekidac za lepo gasenje treda */
	private boolean radi = true;
	
	/** Podaci o studentu koji se svaki put salju profesorskom programu */
	private String podaciZaSlanje;
	
	/** IP/host Adresa na kojoj se nalazi profesorski program*/
	private String adresaProfesorskogRacunara;
	


	
	/**
	 * Konstruktor pakuje sve podatke o studentu u jedan string tako da ih lako mogu raspakovati kada stignu kod profesora.
	 *
	 * @param ime ime studenta
	 * @param prezime prezime studenta
	 * @param brojIndeksa broj indeksa studenta
	 * @param adresaProfesorskogRacunara adresa profesorskog racunara
	 */
	
	public PrijavljivacProfesorskomProgramu(String ime, String prezime, String brojIndeksa, String adresaProfesorskogRacunara) {
		this.podaciZaSlanje = ime + "," + prezime + "," + brojIndeksa;
		this.adresaProfesorskogRacunara = adresaProfesorskogRacunara;
	}
	
	@Override
	public void run() {
				
		while(radi){
			
			try {
				Socket konekcija = new Socket(adresaProfesorskogRacunara, 2323);
				PrintWriter socketIzlaz = new PrintWriter(konekcija.getOutputStream(), true);
				socketIzlaz.println(podaciZaSlanje);
				konekcija.close();
				PrikupljanjePodataka.getInstance().setVisible(false);
				Konektovano.getInstance().setVisible(true);
				Thread.sleep(1000);
				
			} catch (Exception e1) {
				//Profesor nije ukljucio program ili je doslo do problema druge vrste
				
				//Vracam formu za popunjavanje podataka
				PrikupljanjePodataka.getInstance().setVisible(true);
				//Sakrivam prozor koji pokazuje da je konekcija aktivna
				Konektovano.getInstance().setVisible(false);
				//Ispisujem poruku o gresci
				JOptionPane.showMessageDialog(PrikupljanjePodataka.getInstance(), "Konekcija sa profesorom nije moguca, pokusajte ponovo");			
				return; // gasim prijavljivac dok se konekcija ponovo ne uspostavi
			}			
		}
	}
	
	public void setRadi(boolean radi){
		this.radi = radi;
	}

}
