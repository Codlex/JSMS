package com.codlex.jsms.client.gui.paneli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel koji prikazuje pozadinu za zaglavlje prozora.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public class Zaglavlje extends JPanel {
	private static final long serialVersionUID = 1L;
	Image slika;
	String lokacijaPozadinskeSlike = "/resources/gui/header-login.png";
	
	public Zaglavlje() {
		// osnovna podesavanja
		setBackground(new Color(00,0x93,0xd6));
		setPreferredSize(new Dimension(348,137));
		// ucitavamo pozadisnku sliku za zaglavlje
		try {
			slika = ImageIO.read(getClass().getResource(lokacijaPozadinskeSlike));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Overrajdovan paint metod kako bi iscrtavali pozadinu koju zelimo
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		gg.drawImage(slika, 0, 0, this);
	}
}