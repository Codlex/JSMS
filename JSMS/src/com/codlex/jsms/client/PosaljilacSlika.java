package com.codlex.jsms.client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;
import com.codlex.jsms.utils.CompressionImageWriter;
/**
 * Ova klasa predstavlja tred koji ce u realnom vremenu osvezavati sliku ulogovanog korisnika na serveru. 
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
	
	@Override
	public void run() {
		// pravimo robota kako bi preko njega uzeli sliku ekrana na kome se izvrsava aplikacija
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		// izracunavamo dimenzije ekrana na osnovu informacija iz sistema
		Dimension dimenzijeEkrana = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle povrsinaEkrana = new Rectangle(dimenzijeEkrana.width, dimenzijeEkrana.height);
		
        // dokle god nije obustavljeno slanje slika trenutnog ekrana mi to radimo
        while(!zaustavljeno) {
        	
        	System.out.println("Zapoceo sam slanje ekrana!");
        	// uzimam sliku trenutnog ekrana
        	BufferedImage slikaEkrana = robot.createScreenCapture(povrsinaEkrana);
        	// obavesti server da mu saljemo sliku
        	Poruka poruka = CentralizedServerNIC.getNICService().pingScreen();
        	// mrezni deo sistema nam daje upravljanje mrezom kroz OutputStream
        	OutputStream izlaz = (OutputStream) poruka.getMsgObject();
        	try {
        		// saljemo kompresovanu slikuEkrana na izlaz
        		CompressionImageWriter.jpgLowWrite(slikaEkrana, izlaz);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// zatvaramo konekciju
					izlaz.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        	
        	// cekamo odredjen broj milisekundi i nakon toga ponovo saljemo svoj ekran
        	try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
		
	}
}
