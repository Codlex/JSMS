package com.codlex.jsms.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.codlex.jsms.client.ImagePinger;
import com.codlex.jsms.client.model.FriendListModelImpl;
import com.codlex.jsms.client.model.TabbedPaneFriend;

/**
 * Ova klasa je glavni korisnicki interfejs za profesora. Iz ove klase provesor
 * moze da posmatra studentske ekrane i da izbaci bilo kog studenta sa ispita.
 * 
 * @author Dejan Pekter RN 13/11
 * 
 */

public class MainWindow extends JFrame {

	public MainWindow(String naslov) {
		ImagePinger imagePinger = new ImagePinger();
		Thread imagePingerThread = new Thread(imagePinger);
		imagePingerThread.start();
		FriendListModelImpl.createModel();
		Osvezivac osvezivac = new Osvezivac();
		Thread osvezivacThread = new Thread(osvezivac);
		osvezivacThread.start();
		
		setBackground(new Color(00, 0x93, 0xd6));

		// Uzimam dimenzije ekrana
		Toolkit okruzenje = Toolkit.getDefaultToolkit();
		Dimension velicinaEkrana = okruzenje.getScreenSize();

		this.setTitle(naslov);

		// Ukoliko program nije maximiziran postavljam ga tako da zauzima
		// cetvrtinu ekrana
		this.setSize(velicinaEkrana.width / 2, velicinaEkrana.height / 2);

		// podesavam prozor da bude uvecan preko celog ekrana
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		// ukoliko se ugasi ovaj prozor gasi se ceo program
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Malo dizajna u svingu - podesavanje logoa fakulteta i da se ponavlja
		// gradient pozadina
		PanelZaSliku logo = null;
		PanelZaSliku pozadina = null;
		logo = new PanelZaSliku((new ImageIcon(getClass().getResource(
				"/resources/gui/logo.jpg"))).getImage());

		pozadina = new PanelZaSliku((new ImageIcon(getClass().getResource(
				"/resources/gui/pozadina.jpg"))).getImage());

		// panel za logo fakulteta
		PanelSaBorderLejoutom gornjiPanel = new PanelSaBorderLejoutom();
		gornjiPanel.setPreferredSize(new Dimension(this.getWidth(), 64));
		gornjiPanel.setLayout(new BorderLayout());

		// ubacujem logo na levo
		if (logo != null)
			gornjiPanel.add(logo, BorderLayout.WEST);
		// ostatak panela popunjavam sa gradijent pozadinom
		if (pozadina != null)
			gornjiPanel.add(pozadina, BorderLayout.CENTER);

		// pravim glavni panel i dodajem mu gornji panel
		PanelSaBorderLejoutom glavniPanel = new PanelSaBorderLejoutom();
		glavniPanel.add(gornjiPanel, BorderLayout.NORTH);

		// JTabbedPane modifikovan potrebama programa koji ce da sadrzi sve
		// studente
		JTabbedPane ekrani = FriendListModelImpl.getPane();
		/*
		 * Student.setSadrzalacStudenata(ekrani);
		 */
		// podrazumevani tab dok se studenti ne konektuju

		ekrani.setForeground(Color.BLACK);
		ekrani.setBackground(new Color(00, 0x93, 0xd6));

		// ubacujemo StudenteIEkrane na glavi panel
		glavniPanel.add(ekrani, BorderLayout.CENTER);
		// stavljam na glavni prozor ovaj panel
		this.add(glavniPanel);

		// Osvezivac je tred koji osvezava sliku ekrana selektovanog studenta
		/*
		 * Thread osvezivac = new Thread(new Osvezivac(ekrani));
		 * osvezivac.start();
		 */
	}

	private class PanelSaBorderLejoutom extends JPanel {
		public PanelSaBorderLejoutom() {
			setBackground(new Color(00, 0x93, 0xd6));
			this.setLayout(new BorderLayout());
		}
	}

	private class PanelZaSliku extends JPanel {
		private Image slika;

		public PanelZaSliku(Image slika) {
			super();
			setBackground(new Color(00, 0x93, 0xd6));
			this.slika = slika;
			// podesavanje velicine panela na velicinu slike
			this.setPreferredSize(new Dimension(slika.getWidth(this), slika
					.getHeight(this)));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// iscrtavanje slike
			g.drawImage(slika, 0, 0, this.getWidth(), slika.getHeight(this),
					this);

		}

	}

}