package com.codlex.profesor.profesorskooko;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

/**
 * 
 * Ova klasa reprezentuje jednog od studenata koji se posmatraju i sve metode za rad 
 * sa studentom su sadrzane u ovoj klasi.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */
public class Student extends JPanel  {
	
	//slika ekrana konkretnog studenta
	private BufferedImage ekran;
	//adresa racunara na kom je pokrenut studentski program
	private String adresa;
	// ova polja su samoobjasnjiva
	private String imeStudenta;
	private String prezimeStudenta;
	private String brojIndeksa;
	
	// startno vreme konekcije podesavam na 2s
	private int preostaloVremeKonekcije = 2000;
	
	// referenca na tabbedpane u kome drzim sve studente
	private static JTabbedPane sadrzalacStudenata;
	
	private void produziKonekciju(){
		//produzavanje konekcije vrsim resetovanjem na pocentu vrednost od 2s
		//stitim se tako od overflow-a
		preostaloVremeKonekcije=2000;
		
	}
	
	static public void setSadrzalacStudenata(JTabbedPane sadrzalacStudenata) {
		Student.sadrzalacStudenata = sadrzalacStudenata;
	}
	/**
	 * Ovu metodu koristim kako bih proverio da li je student vec na sistemu ili 
	 * ga ubacujem kao novokonektovanog.
	 * 
	 * @param podaci zapakovani podaci o studentu koji su ujedno i njegova identifikacija
	 * @param adresa adresa studentskog racunara 
	 */
	
	static public void prijaviNaSistem(String podaci, String adresa){
				
		Student student = null;
		
		if((student = nadjiStudenta(podaci)) == null ){
			//u ovom slucaju student ne postoji i treba napraviti novog
			student = new Student(podaci, adresa );
		} else {
			//ako student postoji samo mu produzim konekciju
			student.produziKonekciju();
		}
	}
	
	/**
	 * Metoda trazi studenta u jtabbedpane
	 * 
	 * @param ime prosledjuje se ime studenta koji se trazi
	 * @return vraca se komponenta student ili -1 ukoliko student ne postoji
	 * 
	 */
	static public Student nadjiStudenta(String identifikacija){
		
		int index = sadrzalacStudenata.indexOfTab(identifikacija);
		
		if(index == -1)
			return null;
		else 
			return (Student) sadrzalacStudenata.getComponentAt(index);
		
	}
	
	public void smanjiVremeKonekcije(){
		
		preostaloVremeKonekcije-=200;
		
	}
	
	public int getPreostaloVremeKonekcije(){
		return preostaloVremeKonekcije;
	}
	
	public Student(String podaci, String adresa){
		super();
		//raspakujem podatke sa socket-a
		String[] podaciRaspakovani = podaci.split(",");
		
		//raspakovani podaci
		this.adresa = adresa;
		this.imeStudenta = podaciRaspakovani[0];
		this.prezimeStudenta = podaciRaspakovani[1];
		this.brojIndeksa = podaciRaspakovani[2];
		
		//inicijalizujem tajmer za odbrojavanje trajanja konekcije
		Thread timer = new Thread(new timerZaTrajanjeKonekcije(this));
		timer.start();
		
		//uzimam jednu sliku ekrana
		this.osvezi();
		//dodajem studenta na jtabbedpane
		sadrzalacStudenata.add(podaci, this);
		
		// ovde je definisana komponenta u tab-u jtabbedpane-a
		Component sazetakStudenta = new SazetakStudenta();
		sadrzalacStudenata.setTabComponentAt(sadrzalacStudenata.indexOfComponent(this), sazetakStudenta);
		
		//novo konektovanog studenta stavljam kao selektovanog 
		//kako bih profesoru naglasio da se novi student konektovao
		sadrzalacStudenata.setSelectedComponent(this);
		
	}
	
	// Klasa koja objedinjuje komponentu  za potrebe izgleda taba u jtabbedpane-u
	private class SazetakStudenta extends JPanel implements ActionListener{

		public SazetakStudenta(){
			
			//Podesavanje panela
			
			//stavljam providni pane
			setOpaque(false);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS ));
			this.setPreferredSize(new Dimension(140, 90));
			
			//sredjivanje prikaza teksta
			Font veliki = new Font("Arial", 1, 20);
			
			JLabel indeks = new JLabel(brojIndeksa);
			indeks.setFont(veliki);
			this.add(indeks);
			
			this.add(new JLabel("Ime: " + imeStudenta ));
			this.add(new JLabel("Prezime: " + prezimeStudenta ));
			
			// dugme za izbacivanje studenta
			JButton izbacivanjeStudenta = new JButton("Izbaci studenta");
			izbacivanjeStudenta.addActionListener(this);
			this.add(izbacivanjeStudenta);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// izbaci studenta
			Socket konekcija;
			try {
				konekcija = new Socket(adresa, 2020);
				//prepakujem socket u printwriter kako bih dobio mogucnost direktnog ispisivanja stringa
				PrintWriter socketIzlaz = new PrintWriter(konekcija.getOutputStream(), true);
				socketIzlaz.println("izbaceni ste");
			} catch (Exception izuzetak) {
				//Ukoliko nije moguce izbaciti studenta
				JOptionPane.showMessageDialog(this, "Nije moguce izbaciti studenta");
				izuzetak.printStackTrace();
			}
			//gasim konekciju
			preostaloVremeKonekcije = 0;
			// skidam studenta sa sadrzaocaStudenata
			unisti();
		}
	}
	
	/**
	 * Metoda osvezava sliku racunara studenta
	 * 
	 * */
	public void osvezi(){
		
		Socket konekcija;
		try {
			konekcija = new Socket(adresa, 2020);
			//prepakujem socket u PrintWriter kako bih lako poslao komandu u obliku stringa
			PrintWriter socketIzlaz = new PrintWriter(konekcija.getOutputStream(), true);
			socketIzlaz.println("daj mi slicicu");
			
			//ucitavam sliku sa socketa
			ekran = ImageIO.read(konekcija.getInputStream());
			
		} catch (Exception e) {
			// Ignorisem nemogucnost citanja slike jer ce ukoliko se to desi druga klasa da razresi
			// Konekcija se gasi, sa mozda nekim manjim odstupanjem pa bi ovde obrada izuzetka samo
			// napravila problem
		}		
		
		this.repaint();
	};	
	
	
	/**
	 *
	 * Klasa za iscrtavanje ekrana studenta, koristio sam biblioteku za resize slike kako bih povecao
	 * kvalitet slike i laksu promenu velicine slike.
	 * 
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;
		
		
		//uzimam velicinu slike u double zbog preciznijeg deljenja
		double sirinaSlike = ekran.getWidth(this);
		double visinaSlike = ekran.getHeight(this);
		
		//uzimam sirinu prostora za smestanje slike
		int zeljenaSirina =  this.getWidth();
		int zeljenaVisina =  this.getHeight();
		
		//malo matematike za iskoristavanje optimalnog prostora i odrzavanja odnosa
		//visina/sirina slike
		double novaSirinaSlike = Math.min(sirinaSlike, zeljenaSirina);
		double novaVisinaSlike = novaSirinaSlike * (visinaSlike / sirinaSlike);
		
		novaVisinaSlike = Math.min(novaVisinaSlike, zeljenaVisina);
		novaSirinaSlike = novaVisinaSlike * (sirinaSlike/visinaSlike);
		
		//ovde koristim externu biblioteku za promenu velicine slike kako bih dobio na kvalitetu
		BufferedImage scaledImg = Scalr.resize((BufferedImage)ekran, Scalr.Mode.FIT_EXACT, (int)novaSirinaSlike,(int) novaVisinaSlike );
		
		//izracunavam koliko da odaljim sliku od gornje odnosno desne ivice tako da bude centrirana
		int top = (int)((this.getHeight() - scaledImg.getHeight()) / 2.0);
		int left = (int)((this.getWidth() - scaledImg.getWidth()) / 2.0);
		
		//izcrtavam sliku
		gg.drawImage(scaledImg, left, top,  this);
		
	}
	
	//skidam studenta sa sadrzaoca studenata
	public void unisti(){
		sadrzalacStudenata.remove(this);
		
	}

}
