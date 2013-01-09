package com.codlex.jsms.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarInputStream;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.codlex.jsms.client.gui.paneli.Zaglavlje;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;

public class ProzorZaPrijavljivanje extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private PanelZaPrijavljivanje panelZaPrijavljivanje;
	
	public ProzorZaPrijavljivanje() {
		// osnovna podesavanja
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Codlex oko :: prijavljivanje");
		setBackground(new Color(00,0x93,0xd6));
		setPreferredSize(new Dimension(348, 528));
		setSize(348, 528);
		setResizable(false);
		panelZaPrijavljivanje = new PanelZaPrijavljivanje();
		add(panelZaPrijavljivanje);
	}
	/**
	 * Privatna klasa koja je glavni panel u prozoru za prijavljivanje
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 * 
	 */
	private class PanelZaPrijavljivanje extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private PanelZaPrijavljivanjeSaInputElementima panelZaPrijavljivanjeSaElementima;
		private NapraviNalogDugme napraviNalogDugme;
		
		public PanelZaPrijavljivanjeSaInputElementima getPanelZaPrijavljivanjeSaInputElementima() {
			return panelZaPrijavljivanjeSaElementima;
		}
		
		public PanelZaPrijavljivanje() {
			// osnovna podesavanja
			setBackground(new Color(00,0x93,0xd6));
			setBackground(null);
			setLayout(new BorderLayout());
			add(new Zaglavlje(), BorderLayout.NORTH);
			panelZaPrijavljivanjeSaElementima = new PanelZaPrijavljivanjeSaInputElementima();
			add(panelZaPrijavljivanjeSaElementima, BorderLayout.CENTER);
			napraviNalogDugme = new NapraviNalogDugme();
			add(napraviNalogDugme, BorderLayout.SOUTH);
		}
		
	}
	/**
	 * Ugnjezdeni panel za prijavljivanje na sistem sa input elementima.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class PanelZaPrijavljivanjeSaInputElementima extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private KorisnickoIme korisnickoIme;
		private Lozinka lozinka;
		private Prijavljivanje dugmeZaPrijavljivanje;
		
		public String getKorisnickoIme() {
			return korisnickoIme.getKorisnickoIme();
		}
		public String getLozinka() {
			return lozinka.getLozinka();
		}
		
		public PanelZaPrijavljivanjeSaInputElementima() {
			// osnovna podesavanja
			setBackground(new Color(00,0x93,0xd6));
			setLayout(new GridLayout(3, 1));
			korisnickoIme = new KorisnickoIme();
			lozinka = new Lozinka();
			dugmeZaPrijavljivanje = new Prijavljivanje();
			add(korisnickoIme);
			add(lozinka);
			add(dugmeZaPrijavljivanje);
		}
	}
	

	/**
	 * Umotan ulazni elemenat sa JPanelom kako bi se dobilo stilski lep raspored.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class KorisnickoIme extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextField text;
		private JLabel labela;
		private JPanel omotac;
		public KorisnickoIme() {
			// osnovna podesavanja panela
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			// pravimo labelu za username
			labela = new JLabel("username:");
			labela.setForeground(Color.WHITE);
			labela.setBackground(new Color(00,0x93,0xd6));
			// ulaz za tekst
			text = new JTextField();
			// omotac za labelu i text da budu centrirani a opet poravnati
			omotac = new JPanel();
			omotac.setBackground(new Color(00,0x93,0xd6));
			omotac.setLayout(new GridLayout(2,1));
			omotac.setPreferredSize(new Dimension(248,50));
			omotac.add(labela);
			omotac.add(text);
			add(omotac);
		}
		
		public String getKorisnickoIme() {
			return text.getText();
		}
		
	}
	
	/**
	 * Umotan ulazni elemenat sa JPanelom kako bi se dobilo stilski lep raspored.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class Lozinka extends JPanel {
		private static final long serialVersionUID = 1L;
		JPasswordField text;
		JLabel labela;
		JPanel omotac;
		public Lozinka() {
			//osnovna podesavanja
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			// pravimo labelu za lozinku
			labela = new JLabel("lozinka:");
			labela.setForeground(Color.WHITE);
			labela.setBackground(new Color(00,0x93,0xd6));
			// ulaz za lozinku
			text = new JPasswordField();
			// omotac tako da bude centrirano a opet poravnato
			omotac = new JPanel();
			omotac.setBackground(new Color(00,0x93,0xd6));
			omotac.setLayout(new GridLayout(2,1));
			omotac.setPreferredSize(new Dimension(248,50));
			omotac.add(labela);
			omotac.add(text);
			add(omotac);
		}
		
		public String getLozinka() {
			return new String(text.getPassword());
		}
	}
	
	/**
	 * Umotano dugme sa JPanelom kako bi se dobilo stilski lep raspored.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	
	private class Prijavljivanje extends JPanel {
		private static final long serialVersionUID = 1L;
		private JButton dugme;
		
		private static final String putanjaDoIkonice = "/resources/gui/login-login.png";
	
		public Prijavljivanje() {
			// osnovna podesavanja
			setBackground(new Color(00,0x93,0xd6));
		    setLayout(new FlowLayout(FlowLayout.CENTER));
		    // dugme za prijavljivanje
		    dugme = new JButton(new ImageIcon(getClass().getResource(putanjaDoIkonice)));
			dugme.setPreferredSize(new Dimension(248, 39));
			dugme.setFocusPainted(false);
			dugme.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String korisnickoIme = panelZaPrijavljivanje.getPanelZaPrijavljivanjeSaInputElementima().getKorisnickoIme();
					String lozinka = panelZaPrijavljivanje.getPanelZaPrijavljivanjeSaInputElementima().getLozinka();
					// logovanje korisnika na sistem tako sto se obracamo serveru
					Poruka odgovor = getNICService().logIn(new BaseUser(korisnickoIme, lozinka));
					// ukoliko je korisnik uspesno ulogovan vraca se poruka SUCCESS sa njegovim tokenom
					if( odgovor.getKodPoruke() == MSGCode.SUCCESS ) {
						System.out.println("Korisnik je ulogovan!");
						// resavamo se prozora za prijavljivanje
						ProzorZaPrijavljivanje.this.setVisible(false);
						ProzorZaPrijavljivanje.this.dispose();
						// prikazujemo glavni prozor
						new GlavniProzor("Codlex oko").setVisible(true);
					}
					else {
						System.out.println("Pogresno korisnicko ime ili lozinka!");
						JOptionPane.showMessageDialog(ProzorZaPrijavljivanje.this, "Nazalost ukucali ste pogresno korisnicko ime ili lozinku, pokusajte ponovo :)" );
					}
				}
			});
			add(dugme);
			
		}
	}
	
	/**
	 * Umotano dugme sa JPanelom kako bi se dobilo stilski lep raspored.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class NapraviNalogDugme extends JPanel {
		private static final long serialVersionUID = 1L;
		private JButton dugme;
		private static final String putanjaDoIkonice = "/resources/gui/napravi-nalog.png";
	
		public NapraviNalogDugme() {
			// osnovno podesavanje
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			dugme = new JButton(new ImageIcon(getClass().getResource(putanjaDoIkonice)));
			dugme.setBackground(new Color(00,0x93,0xd6));
			dugme.setPreferredSize(new Dimension(348, 79));
			dugme.setFocusPainted(false);
			dugme.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// resavamo se prozora za logovanje
					ProzorZaPrijavljivanje.this.setVisible(false);
					ProzorZaPrijavljivanje.this.dispose();
					// palimo prozor za pravljenje naloga
					new ProzorZaPravljenjeNaloga().setVisible(true);
				}
			});
			add(dugme);
		}
	}
	


}
