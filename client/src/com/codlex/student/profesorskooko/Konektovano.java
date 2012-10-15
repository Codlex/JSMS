package com.codlex.student.profesorskooko;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasa Konektovano sluzi kako bi prikazala poruku studentu ukoliko je konekcija na profesorski program uspesno izvrsena.
 * Ovaj prozor zivi sve dok konekcija ne pukne ili student ne zatvori taj program, cime se i ceo program zatvara.
 * 
 */
public class Konektovano extends JFrame{
	
	/**
	 * Ovo je konstrukcija klase po singleton paternu.
	 * Kako bih ostao standarizovan sa patternom namerno nisam promenuo naziv public metode getInstance() jer nisam nasao ni jedan adekvatan termin u nasem jeziku.
	*/
	
	private Konektovano(){
		super();
		//podesavanje frejma
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(new SredinaEkrana(320, 120));
		this.setResizable(false);
		//dodavanje panela
		this.add(new KonektovanoTekst());
		
	}
	private static Konektovano self = new Konektovano(); 
	public static Konektovano getInstance(){
		return self;
	}
	
	
	
	/**
	 * Klasa KonektovanoTekst umotava sve potrebne stvari za stil teksta i njegov prikaz
	 */
	private class KonektovanoTekst extends JPanel{
		
		public KonektovanoTekst(){
			
			JLabel naslov = new JLabel("Konektovani ste!"); 
			//Podesavanje fonta i pozadine
			Font font = new Font(Font.SERIF, Font.BOLD, 30);
			naslov.setFont(font);
			naslov.setForeground(Color.WHITE);
			
			JLabel poruka = new JLabel("Mozete spustiti prozor i poceti sa izradom ispita.");
			//Podesavanje boje
			poruka.setForeground(Color.WHITE);
			
			
			//podesavanje panela
			this.setBackground( new Color(181, 230, 29) );
			this.add(naslov);			
			this.add(poruka);
		}
	}
}
