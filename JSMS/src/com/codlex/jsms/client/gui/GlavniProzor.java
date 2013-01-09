package com.codlex.jsms.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.codlex.jsms.client.PosaljilacSlika;
import com.codlex.jsms.client.gui.paneli.PanelSaBorderLejautom;
import com.codlex.jsms.client.gui.paneli.PanelZaSliku;
import com.codlex.jsms.client.model.ModelListePrijateljaImplementacija;
import com.codlex.jsms.client.model.TabbedPanePrijatelj;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

/**
 * Glavni prozor u kome se nalazi lista prijatelja ulogovanog korisnika kao i ekran trenutno selektovanog korisnika.
 * Korisnik je sa ovog prozora u mogucnosti da se izloguje i da doda novog prijatelja.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 * 
 */

public class GlavniProzor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTabbedPane ekrani;
	PosaljilacSlika posiljalacSlika;
	Osvezivac osvezivac;
	
	public GlavniProzor(String naslov) {
		// ukljucujemo tred koji osvezava sliku ovog korisnika na serveru
		posiljalacSlika = new PosaljilacSlika();
		Thread tredPosiljaocaSlika = new Thread(posiljalacSlika);
		tredPosiljaocaSlika.start();
		// pravimo model korisnikovih prijatelja
		// to je moguce sada uraditi posto je u mreznom sloju upisan korisnikov token 
		ModelListePrijateljaImplementacija.napraviModel();
		
		// ukljucujemo tred koji osvezava sliku trenutno selektovanog prijatelja u glavnom prozoru
		osvezivac = new Osvezivac();
		Thread osvezivacTred = new Thread(osvezivac);
		osvezivacTred.start();
		

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
		PanelSaBorderLejautom gornjiPanel = new PanelSaBorderLejautom();
		gornjiPanel.setPreferredSize(new Dimension(this.getWidth(), 64));
		gornjiPanel.setLayout(new BorderLayout());

		// ubacujem logo na levo
		if (logo != null)
			gornjiPanel.add(logo, BorderLayout.WEST);
		// ostatak panela popunjavam sa gradijent pozadinom
		if (pozadina != null)
			gornjiPanel.add(pozadina, BorderLayout.CENTER);

		// pravim glavni panel i dodajem mu gornji panel
		PanelSaBorderLejautom glavniPanel = new PanelSaBorderLejautom();
		glavniPanel.add(gornjiPanel, BorderLayout.NORTH);

		// JTabbedPane modifikovan potrebama programa koji ce da sadrzi sve
		// prijatelje
		ekrani = ModelListePrijateljaImplementacija.getPane();
		ekrani.setForeground(Color.BLACK);
		glavniPanel.add(ekrani, BorderLayout.CENTER);
		
		
		// panel za izlogovanje i dodavanje prijatelja
		PanelSaBorderLejautom panelSaDugmicima = new PanelSaBorderLejautom();
		// dodavanje prijatelja dugme
		JButton dodajPrijatelja = new JButton("Dodaj prijatelja");
		dodajPrijatelja.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String korisnickoIme = JOptionPane.showInputDialog(GlavniProzor.this, "Unesite username prijatelja kojeg zelite da dodate:");
				Poruka odgovor = CentralizovaniNIC.getNICService().dodajPrijatelja(korisnickoIme);
				if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)) {
					ekrani.add(korisnickoIme, new TabbedPanePrijatelj(korisnickoIme, ekrani));
				} 
				else {
					JOptionPane.showMessageDialog(GlavniProzor.this, "Username koji ste uneli nije moguce dodati!");
				}
			}
		});
		
		panelSaDugmicima.add(dodajPrijatelja, BorderLayout.WEST);
		// dugme za napustanje sistema
		JButton izadji = new JButton("Izadji");
		izadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// resava se prozora i cisti iza sebe
				izlogujSe();
			}
		});
		panelSaDugmicima.add(izadji, BorderLayout.EAST);
		add(panelSaDugmicima, BorderLayout.SOUTH);
		// stavljam na glavni prozor ovaj panel
		this.add(glavniPanel);
	}



	

	public void izlogujSe() {
		// ugasi posiljaoca slika
		GlavniProzor.this.posiljalacSlika.zaustavljeno();
		GlavniProzor.this.posiljalacSlika = null;
		// ugasi osvezivaca
		GlavniProzor.this.osvezivac.zaustavljeno();
		GlavniProzor.this.osvezivac = null;
		
		// resi se prozora
		GlavniProzor.this.dispose();
		CentralizovaniNIC.getNICService().odjaviSe();
		new ProzorZaPrijavljivanje().setVisible(true);
	}

}