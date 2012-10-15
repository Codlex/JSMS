package com.codlex.student.profesorskooko;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasa IzbaceniStudent iscrtava studentu zalosnu vest da je izbacen sa ispita zbog prepisivanja ili nekog drugog prekrsenog pravila. 
 *
 * @author Dejan Pekter RN 13/11
 */

public class IzbaceniStudent extends JFrame {
	
	/**
	 * Konstruise JFrame za poruku i ubacuje na njega PanelZaIzbacene
	 * 
	 * @see PanelZaIzbacene
	 */
	public IzbaceniStudent(){
		super();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(new SredinaEkrana(500, 450));
		this.setTitle("Izbaceni ste sa ispita");
		this.add(new PanelZaIzbacene());
		
		this.setVisible(true);
	}
	
	/**
	 * Privatna klasa PanelZaIzbacene klase IzbaceniStudent je JPanel koji sadrzi tuznu sliku i poruku studentu koji je izbacen zbog sa ispita.
	 * 
	 * @author Dejan Pekter RN 13/11
	 */
	private class PanelZaIzbacene extends JPanel {
		
		/**
		 * Konstuise novi panel tako sto baferuje sliku i pravi labelu, koje onda postavlja po BorderLayout-u tako da se poruka nalazi na vrhu, 
		 * a slika zauzima ostatak prozora (podrazumevano ponasanje BorderLayout-a).
		 */
		
		public PanelZaIzbacene() {
			
			this.setLayout(new BorderLayout());
			
			//Dodaje poruku
			this.add(new Poruka(), BorderLayout.NORTH);
			
			// Ucitava i dodaje sliku
			Image slika = new ImageIcon( getClass().getResource("tuzan-student.jpg") ).getImage();
			PanelZaSliku panelSlika = new PanelZaSliku(slika);
			this.add(panelSlika, BorderLayout.CENTER );
		}
	}
	
	
	/**
	 * Privatna klasa Poruka predstavlja panel koji umotava poruku kako bi omogucio promenu grafickog prikaza poruke na jednom mestu.
	 * 
	 */
	private class Poruka extends JPanel{
		
		/**
		 * Konstrukcija poruke studentu koji je izbacen sa predavanja, sa upozoravajucom rozom bojom pozadine i belim slovima.
		 */
		
		public Poruka(){
			this.setPreferredSize(new Dimension(0, 150)); //sirina nebitna visina se podesava
			this.setBackground(Color.pink);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//Konstrukcija poruke i dodavanje na panel
			this.add(new TekstStil("Izbaceni ste sa ispita"));
			this.add(new TekstStil("napustite prostoriju"));
			this.add(new TekstStil("manje prepisivanja sledeci put"));
		}
		
		
	}
	
	/**
	 * TekstStil takodje privatna klasa koja omogucava jedinstvena podesavanja fonta za sve poruke koje zelimo da prikazemo.
	 */
	private class TekstStil extends JLabel{
		
		/**
		 * Konstruktor za tekst odredjenog stila.
		 *
		 * @param tekst tekst koji treba da se ispise
		 */
		public TekstStil(String tekst){
			super(tekst);
			
			setAlignmentX(CENTER_ALIGNMENT);
			this.setForeground(Color.WHITE);
			
			//podesavanje fonta
			Font mojFont = new Font(Font.SERIF, 1, 30);
			this.setFont(mojFont);
		}
	}
	
	//Test metoda main je izbacena nakon zavrsetka projekta

}
