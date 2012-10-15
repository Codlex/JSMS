package com.codlex.profesor.profesorskooko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Ova klasa prihvata konekcije studenata i smesta ih u JTabbedPane ili 
 * osvezuje njihovo vreme postojanja konekcije.
 * 
 * @author Dejan Pekter
 *
 */

public class PrihvatilacKonekcija implements Runnable {
	
	@Override
	public void run() {		
		try {
			ServerSocket primalac = new ServerSocket(2323);
			
			while(true){
				//prihvatanje konekcija sa serverskog socketa
				Socket konekcija = primalac.accept();
				//prepakujem socket u BufferedReader za laksi rad sa stringom
				InputStreamReader socketUlaz = new InputStreamReader(konekcija.getInputStream());
				BufferedReader socketBufUlaz = new  BufferedReader(socketUlaz);
				String podaci = socketBufUlaz.readLine();
				//metoda klase student koja sluzi za prijavljivanje novog ili produzenje konekcije poostojeceg studenta
				Student.prijaviNaSistem(podaci, konekcija.getInetAddress().getHostAddress());
				
				konekcija.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nije moguce slusati na portu 2323, proverite da niste dva puta pokrenuli program");
			System.exit(-1);
		}
		
		
	}
	

}
