package com.codlex.jsms.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

/**
 * 
 * 
 * @author Dejan Pekter RN 13/11
 * 
 */

public class MainWindow extends JFrame {
	JTabbedPane ekrani;
	ImagePinger imagePinger;
	public MainWindow(String naslov) {
		imagePinger = new ImagePinger();
		Thread imagePingerThread = new Thread(imagePinger);
		imagePingerThread.start();
		FriendListModelImpl.createModel();
		Osvezivac osvezivac = new Osvezivac();
		Thread osvezivacThread = new Thread(osvezivac);
		osvezivacThread.start();
		

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
		ekrani = FriendListModelImpl.getPane();
		/*
		 * Student.setSadrzalacStudenata(ekrani);
		 */
		// podrazumevani tab dok se studenti ne konektuju

		ekrani.setForeground(Color.BLACK);

		// ubacujemo StudenteIEkrane na glavi panel
		glavniPanel.add(ekrani, BorderLayout.CENTER);
		
		
		// panel za izlogovanje i dodavanje prijatelja
		PanelSaBorderLejoutom panelSaDugmicima = new PanelSaBorderLejoutom();
		
		// dodavanje prijatelja dugme
		JButton dodajPrijatelja = new JButton("Dodaj prijatelja");
		dodajPrijatelja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = JOptionPane.showInputDialog("Unesite username prijatelja kojeg zelite da dodate:");
				Message response = CentralizedServerNIC.getNICService().addFriend(username);
				if(response.getMsgCode().equals(MSGCode.SUCCESS)) {
					ekrani.add(username, new TabbedPaneFriend(username, ekrani));
				} 
				else {
					JOptionPane.showMessageDialog(MainWindow.this, "Username koji ste uneli nije moguce dodati!");
				}
			}
		});
		panelSaDugmicima.add(dodajPrijatelja, BorderLayout.WEST);
		
		JButton izadji = new JButton("Izadji");
		izadji.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.this.imagePinger.stop();
				MainWindow.this.dispose();
				new LoginScreen().setVisible(true);
			}
		});
		panelSaDugmicima.add(izadji, BorderLayout.EAST);
		add(panelSaDugmicima, BorderLayout.SOUTH);
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
			this.setLayout(new BorderLayout());
		}
	}

	private class PanelZaSliku extends JPanel {
		private Image slika;

		public PanelZaSliku(Image slika) {
			super();
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