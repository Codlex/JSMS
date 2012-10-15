package com.codlex.student.profesorskooko;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Klasa PrikupljanjePodataka cini glavni korisnicki interfejs studentskog programa.
 * U njoj se ucitavaju informacije o studentu i daje mu se mogucnost da se konektuje na profesorski program.
 */
public class PrikupljanjePodataka extends JFrame {
	
	
	/** Naslov programa */
	private String title = "Podaci o studentu";
	
	/** Referenca na prijavljivac kako bi mu mogao pristupati iz cele klase */
	private PrijavljivacProfesorskomProgramu prijavljivac = null;
	
	

	/**
	 * Ovo je konstrukcija klase po singleton paternu.
	 * Kako bih ostao standarizovan sa patternom namerno nisam promenuo naziv public metode getInstance() jer nisam nasao ni jedan adekvatan termin u nasem jeziku.
	*/
	public static PrikupljanjePodataka self = new PrikupljanjePodataka();
	public static PrikupljanjePodataka getInstance(){
		return self;
	}
	private PrikupljanjePodataka() {
		//podesavanje frejma
		this.self = this;
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(new SredinaEkrana(340, 500));
		
		//dodavanje panela
		GlavniPanel panel = new GlavniPanel();
		this.add(panel);
		
		this.setVisible(true);
	}
		
	

	
	/**
	 * Glavni panel sadrzi sve ulazne podatke za studenta, iz njega se instancira ovi objekat prijavljivaca profesoru.
	 */
	private class GlavniPanel extends JPanel implements ActionListener {
		
		//ulazni paneli 
		/** The Adresa profesorskog racunara */
		UlazPanel adresaServera = new UlazPanel();
		UlazPanel brojIndeksa = new UlazPanel();
		UlazPanel prezimePanel = new UlazPanel();
		UlazPanel imePanel = new UlazPanel();
		
		public GlavniPanel(){
			//podesavanja panela
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//ucitavam i dodajem logo fakulteta
			Image logo = new ImageIcon(	getClass().getResource("logo.jpg")).getImage();
			this.add(new PanelZaSliku(logo));
			
			//dodavanje ulaznih komponenti
			this.add(new NazivPanel("Ime:"));
			this.add(imePanel);
			
			this.add(new NazivPanel("Prezime:"));
			this.add(prezimePanel);
			
			this.add(new NazivPanel("Broj indeksa:"));
			this.add(brojIndeksa);
			
			this.add(new NazivPanel("Adresa profesorskog racunara:"));
			this.add(adresaServera);
			
			// moje resenje jednog grafickog problema koje nisam uspeo ni sa jednim layoutmanager-om da resim
			this.add(new ZauzmiProstor(50));
			
			//dugme za prihvatanje forme
			JButton dugme = new JButton("Konektuj se");
			dugme.setAlignmentX(CENTER_ALIGNMENT);
			dugme.addActionListener((ActionListener) this);
			
			this.add(dugme);
		}
		
		
		public void actionPerformed(ActionEvent e) {
			// Student je kliknuo na dugme i  pokusava da se konektuje na profesorski program (startujem prijavljivac profesoru) 
			prijavljivac = new PrijavljivacProfesorskomProgramu(imePanel.getVrednost(), prezimePanel.getVrednost(), brojIndeksa.getVrednost(), adresaServera.getVrednost());
			Thread prijavljivacTred = new Thread(prijavljivac);
			prijavljivacTred.start();
		}	
	}
	
	public PrijavljivacProfesorskomProgramu getPrijavljivac() {
		return prijavljivac;
	}
	
	/**
	 *  Popunjava vertikalni prostor odredjen parametrom sa praznim prostorom
	 */
	private class ZauzmiProstor extends JPanel{
		
		/**
		 * Pravi novi prazan prostor.
		 * 
		 * @param height visina koju treba popuniti prazninom
		 */
		public ZauzmiProstor(int height){
			this.setSize(0, height);
			this.setPreferredSize(new Dimension(340, height));
		}
	}
	
	// Graficke komponente koje se objedinjuju u male klase slede
	// smatram da komentari dalje nisu potrebni jer se iz imena promenljivih sve razume
	
	
	private abstract class PanelZaIzgled extends JPanel{
		public PanelZaIzgled(){
			this.setLayout(new FlowLayout());
			this.setAlignmentX(CENTER_ALIGNMENT);
		}
	}
	
	
	private class UlazPanel extends PanelZaIzgled{
		
		//ulazno polje
		private JTextField polje = new JTextField(20);
		
		public String getVrednost(){
			return polje.getText();
		}
		
		public UlazPanel(){
			super();
			this.add(polje);
		}
	}
	
	private class NazivPanel extends PanelZaIzgled{		
		
		public NazivPanel(String naziv){
			super();
			this.add(new Label(naziv));			
		}
	}
	
}
