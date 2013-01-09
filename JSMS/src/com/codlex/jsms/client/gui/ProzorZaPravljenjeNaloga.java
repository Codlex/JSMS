package com.codlex.jsms.client.gui;

import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.getNICService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import com.codlex.jsms.networking.users.OsnovniKorisnik;

/**
 * Ova klasa predstavlja prozor za pravljenje naloga.
 * 
 * @author Dejan Pekter <dpekter@raf.edu.rs>
 *
 */
public class ProzorZaPravljenjeNaloga extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private PanelZaPravljenjeNaloga panelZaPravljenjeNaloga;
	
	public ProzorZaPravljenjeNaloga() {
		// zatvarnje ovog prozora treba da znaci zatvaranje aplikacije
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// podesavamo osnove stvari prozora
		setTitle("Codlex oko :: Napravi nalog");
		setBackground(new Color(00,0x93,0xd6));
		setPreferredSize(new Dimension(348, 580));
		setSize(348, 580);
		setResizable(false);
		// pravimo novi panel za pravljenje naloga
		panelZaPravljenjeNaloga = new PanelZaPravljenjeNaloga();
		add(panelZaPravljenjeNaloga);
	}
	
	/**
	 * Glavni panel za pravljenje naloga.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class PanelZaPravljenjeNaloga extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private UgnjezdenPanelZaPravljenjeNalogaSaInputElementima panelZaPravljenjeNalogaSaInputElementima;
		private DugmeZaPovratakNaProzorZaPrijavljivanje vratiSeNaProzorZaPrijavljivanje;
		
		public UgnjezdenPanelZaPravljenjeNalogaSaInputElementima getProzorSaInputomZaPravljenjeNaloga() {
			return panelZaPravljenjeNalogaSaInputElementima;
		}
		
		public PanelZaPravljenjeNaloga() {
			// osnovna podesavanja panela
			setBackground(new Color(00,0x93,0xd6));
			setBackground(null);
			setLayout(new BorderLayout());
			// dodajemo zaglavlje panela
			add(new Zaglavlje(), BorderLayout.NORTH);
			panelZaPravljenjeNalogaSaInputElementima = new UgnjezdenPanelZaPravljenjeNalogaSaInputElementima();
			// dodajemo panel koji ce sadrzati input elemente
			add(panelZaPravljenjeNalogaSaInputElementima, BorderLayout.CENTER);
			vratiSeNaProzorZaPrijavljivanje = new DugmeZaPovratakNaProzorZaPrijavljivanje();
			// dodajemo dugme za vracanje na prozor za prijavljivanje
			add(vratiSeNaProzorZaPrijavljivanje, BorderLayout.SOUTH);
		}
		
	}
	/**
	 * Ugnjezdeni panel za pravljenje naloga sa input elementima.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class UgnjezdenPanelZaPravljenjeNalogaSaInputElementima extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private KorisnickoIme korisnickoIme;
		private Lozinka lozinka;
		private Lozinka lozinka2;
		private Email email;
		private DugmeZaPravljenjeNaloga dugmeZaPravljenjeNaloga;
		
		public String getKorisnickoIme() {
			return korisnickoIme.getKorisnickoIme();
		}
		public String getLozinka() {
			return lozinka.getLozinka();
		}
		public String getLozinka2() {
			return lozinka2.getLozinka();
		}
		public String getEmail() {
			return email.getEmail();
		}
		
		public UgnjezdenPanelZaPravljenjeNalogaSaInputElementima() {
			// osnovna podesavanja
			setBackground(new Color(00,0x93,0xd6));
			setLayout(new GridLayout(5, 1));
			// ubacujemo sve panele
			korisnickoIme = new KorisnickoIme();
			lozinka = new Lozinka();
			lozinka2 = new Lozinka();
			email = new Email();
			dugmeZaPravljenjeNaloga = new DugmeZaPravljenjeNaloga();
			add(korisnickoIme);
			add(lozinka);
			add(lozinka2);
			add(email);
			add(dugmeZaPravljenjeNaloga);
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
	private class Email extends JPanel {
		private static final long serialVersionUID = 1L;
		
		JTextField text;
		JLabel labela;
		JPanel omotac;
		
		public Email() {
			//osnovna podesavanja
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			// pravimo labelu za e-mail
			labela = new JLabel("e-mail:");
			labela.setForeground(Color.WHITE);
			labela.setBackground(new Color(00,0x93,0xd6));
			// ulaz za e-mail
			text = new JTextField();
			// panel omotac tako da bude centrirano a poravnato na levo
			omotac = new JPanel();
			omotac.setBackground(new Color(00,0x93,0xd6));
			omotac.setLayout(new GridLayout(2,1));
			omotac.setPreferredSize(new Dimension(248,50));
			omotac.add(labela);
			omotac.add(text);
			add(omotac);
		}
		
		public String getEmail() {
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
	private class DugmeZaPravljenjeNaloga extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton dugme;
		private static final String putanjaDoIkonice = "/resources/gui/create-account.png";
	
		public DugmeZaPravljenjeNaloga() {
			// osnovna podesavanja
			setBackground(new Color(00,0x93,0xd6));
		    setLayout(new FlowLayout(FlowLayout.CENTER));
		    // pravimo dugme sa definisanom ikonicom
		    dugme = new JButton(new ImageIcon(getClass().getResource(putanjaDoIkonice)));
			dugme.setPreferredSize(new Dimension(248, 39));
			dugme.setFocusPainted(false);
			// dodeljujemo akciju dugmetu
			dugme.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String korisnickoIme = panelZaPravljenjeNaloga.getProzorSaInputomZaPravljenjeNaloga().getKorisnickoIme();
					String lozinka = panelZaPravljenjeNaloga.getProzorSaInputomZaPravljenjeNaloga().getLozinka();
					String lozinka2 = panelZaPravljenjeNaloga.getProzorSaInputomZaPravljenjeNaloga().getLozinka2();
					String email = panelZaPravljenjeNaloga.getProzorSaInputomZaPravljenjeNaloga().getEmail();
					
					if(!lozinka.equals(lozinka2)) {
						JOptionPane.showMessageDialog(ProzorZaPravljenjeNaloga.this, "Lozinke nisu iste!");
						return;
					}
					// zamolimo servera da nam napravi nalog
					Poruka odgovor = getNICService().napraviNalog(new OsnovniKorisnik(korisnickoIme, lozinka, email));
					if( odgovor.getKodPoruke() == MSGCode.SUCCESS ) {
						// ukoliko je uspeo, to zapisemo ulog i prikazemo obavestenje korisniku
						System.out.println("Korisnik napravio nalog!");
						JOptionPane.showMessageDialog(ProzorZaPravljenjeNaloga.this, "Napravili ste nalog, sada cete biti ulogovani!");
						// sakrivamo prozor za logovanje
						ProzorZaPravljenjeNaloga.this.setVisible(false);
						ProzorZaPravljenjeNaloga.this.dispose();
						// prikazujemo glavni prozor
						new GlavniProzor("Codlex oko").setVisible(true);
					}
					else {
						// ukoliko nije uspesno kreiranje korisnika znaci da korisnicko ime vec postoji
						System.out.println("Korisnicko ime vec postoji");
						// obavestavamo korisnika o tome
						JOptionPane.showMessageDialog(ProzorZaPravljenjeNaloga.this, "" + odgovor.getKodPoruke() );
					}
				}
			});
			add(dugme);
		}
	}
	/**
	 * Umotan ulazni elemenat sa JPanelom kako bi se dobilo stilski lep raspored.
	 * 
	 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
	 *
	 */
	private class DugmeZaPovratakNaProzorZaPrijavljivanje extends JPanel {
		private static final long serialVersionUID = 1L;
		private JButton dugme;
		private static final String putanjaDoIkonice = "/resources/gui/back.png";
	
		public DugmeZaPovratakNaProzorZaPrijavljivanje() {
			// osnovna podesavanja
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			// pravimo dugme sa definisanom ikonicom
			dugme = new JButton(new ImageIcon(getClass().getResource(putanjaDoIkonice)));
			dugme.setBackground(new Color(00,0x93,0xd6));
			dugme.setPreferredSize(new Dimension(348, 79));
			dugme.setFocusPainted(false);
			// pravimo akciju za dugme
			dugme.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// gasimo ovaj prozor
					ProzorZaPravljenjeNaloga.this.setVisible(false);
					// instanciramo prozor za login
					new ProzorZaPrijavljivanje().setVisible(true);					
				}
			});
			add(dugme);
		}
	}

}
