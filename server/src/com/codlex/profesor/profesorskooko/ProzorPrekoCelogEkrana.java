package com.codlex.profesor.profesorskooko;

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

/**
 * Ova klasa je glavni korisnicki interfejs za profesora. 
 * Iz ove klase provesor moze da posmatra studentske ekrane i
 * da izbaci bilo kog studenta sa ispita.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */

public class ProzorPrekoCelogEkrana extends JFrame {
	
	
	
	public ProzorPrekoCelogEkrana(String naslov){
		//Uzimam dimenzije ekrana
		Toolkit okruzenje = Toolkit.getDefaultToolkit();
		Dimension velicinaEkrana = okruzenje.getScreenSize();
		
		this.setTitle(naslov);
		
		// Ukoliko program nije maximiziran postavljam ga tako da zauzima cetvrtinu ekrana
		this.setSize(velicinaEkrana.width / 2, velicinaEkrana.height / 2);

		//podesavam prozor da bude uvecan preko celog ekrana
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		//ukoliko se ugasi ovaj prozor gasi se ceo program
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Malo dizajna u svingu - podesavanje logoa fakulteta i da se ponavlja gradient pozadina
		PanelZaSliku logo = null;
		PanelZaSliku pozadina = null;
		logo = new PanelZaSliku(
						(new ImageIcon(
								getClass().getResource("logo.jpg"))
						).getImage());
		
		pozadina = new PanelZaSliku(
				(new ImageIcon(
						getClass().getResource("pozadina.jpg"))
				).getImage());

		
		// panel za logo fakulteta
		PanelSaBorderLejoutom gornjiPanel = new PanelSaBorderLejoutom();
		gornjiPanel.setPreferredSize(new Dimension(this.getWidth(), 64));
		gornjiPanel.setLayout(new BorderLayout());
		
		//ubacujem logo na levo
		if(logo != null)
			gornjiPanel.add(logo, BorderLayout.WEST);
		//ostatak panela popunjavam sa gradijent pozadinom
		if(pozadina != null)
			gornjiPanel.add(pozadina, BorderLayout.CENTER);
		
		//pravim glavni panel i dodajem mu gornji panel
		PanelSaBorderLejoutom glavniPanel = new PanelSaBorderLejoutom();
		glavniPanel.add(gornjiPanel, BorderLayout.NORTH);
		
		//JTabbedPane modifikovan potrebama programa koji ce da sadrzi sve studente
		StudentiIEkrani ekrani = new StudentiIEkrani();
		Student.setSadrzalacStudenata(ekrani);
		
		//podrazumevani tab dok se studenti ne konektuju
		ekrani.add("S t u d e n t i", new PodrazumevaniPanel());
		//ubacujemo StudenteIEkrane na glavi panel
		glavniPanel.add(ekrani,BorderLayout.CENTER);
		//stavljam na glavni prozor ovaj panel
		this.add(glavniPanel);
		
		// Osvezivac je tred koji osvezava sliku ekrana selektovanog studenta 
		Thread osvezivac = new Thread(new Osvezivac(ekrani));
		osvezivac.start();
	}
	// Graficki elemenat koji resava zauzima odredjeni prazan vertikalni prostor
	private class ZauzmiProstor extends JPanel{
		/**
		 * 
		 * @param height kolicina vertikalnog prostora koji zelimo da popunimo prazninom
		 */
		public ZauzmiProstor(int height){
			this.setSize(0, height);
			this.setPreferredSize(new Dimension(0, height));
		}
	}
	
	
	// Panel za dobrodoslicu u program tj. tab koji je aktivan dok nema konektovanih studenata
	private class PodrazumevaniPanel extends JPanel {
		public PodrazumevaniPanel(){
			super();
			//podesavanja panela
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//animacija omotana u panelu kako se nebi razvukla
			ImageIcon animacija = new ImageIcon( getClass().getResource("animacija.gif"));
			JPanel omotac = new JPanel();
			omotac.add(new PanelZaSliku(animacija.getImage()));
			this.add(omotac);
			
			//Poruka dobrodoslice - stil i dodavanje na panel
			JLabel dobrodoslica = new JLabel("Dobrodosli u profesorsko oko!");
			dobrodoslica.setAlignmentX(CENTER_ALIGNMENT);
			dobrodoslica.setFont(new Font(Font.SERIF, Font.BOLD, 30) );
			dobrodoslica.setForeground(Color.GRAY);
			this.add(dobrodoslica);
			
			//Tekst dobrodoslice - stil i dodavanje na panel
			JLabel tekst = new JLabel("Molim Vas sacekajte da se studenti konektuju");
			tekst.setAlignmentX(CENTER_ALIGNMENT);
			tekst.setFont(new Font(Font.SERIF, Font.BOLD, 30) );
			tekst.setForeground(Color.GRAY);
			this.add(tekst);
			//odvajanje od dna prozora
			this.add(new ZauzmiProstor(50));
		}		
	}
	
	//ostatak nema potrebe za komentarima imena sama objasnjavaju sve
	
	private class PanelSaBorderLejoutom extends JPanel{	
		public PanelSaBorderLejoutom() {
			this.setLayout(new BorderLayout());
		}
	}
	private class PanelZaSliku extends JPanel{
		private Image slika;
		
		public PanelZaSliku(Image slika){
			super();
			this.slika = slika;	
			//podesavanje velicine panela na velicinu slike
			this.setPreferredSize(new Dimension(slika.getWidth(this), slika.getHeight(this)));			
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			//iscrtavanje slike 
			g.drawImage(slika, 0, 0, this.getWidth(), slika.getHeight(this), this);
			
		}

	}
	
	private class StudentiIEkrani extends JTabbedPane{
		public StudentiIEkrani(){
			super();
			//tabovi na levoj strani
			this.setTabPlacement(StudentiIEkrani.LEFT);
		}	
	}
}
