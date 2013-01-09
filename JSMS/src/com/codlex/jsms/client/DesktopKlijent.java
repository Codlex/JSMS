package com.codlex.jsms.client;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.codlex.jsms.client.gui.ProzorZaPrijavljivanje;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;

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