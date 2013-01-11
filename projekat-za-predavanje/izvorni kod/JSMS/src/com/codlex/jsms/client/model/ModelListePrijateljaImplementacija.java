package com.codlex.jsms.client.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.codlex.jsms.client.gui.paneli.PanelZaSliku;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

public class ModelListePrijateljaImplementacija extends JTabbedPane{

	private static final long serialVersionUID = 1L;
	private static JTabbedPane tabbedPane;
	private static Osvezivac osvezivac;
	private static Thread osvezivacTred;
	
	public ModelListePrijateljaImplementacija(){
            super();
            this.setTabPlacement(ModelListePrijateljaImplementacija.LEFT);
    }       
	
    public static JTabbedPane getPane() {
    	return tabbedPane;
    }
    
    public synchronized static Osvezivac getOsvezivac() {
    	return osvezivac;
    }
    
    /**
     * Pravi model za trenutno ulogovanog korisnika.
     * Podatke o korisniku uzima iz sistema.
     */
    @SuppressWarnings("unchecked")
	public static void napraviModel() {
    	
    	// trazimo od servera listu prijatelja trenutno ulogovanog korisnika
    	Poruka odgovor = CentralizovaniNIC.getNICService().getPrijatelji();
    	// u odgovoru se nalazi lista prijatelja trenutnog korisnika
    	// ukoliko korisnik nema prijatelja vraca se prazna lista
    	Collection<String> prijatelji = (Collection<String>) odgovor.getObjekatPoruke();
    	// instanciramo model
    	tabbedPane = new ModelListePrijateljaImplementacija();
    	// dodeljujemo prvu stavku kao podrazumevani pane dok nema korisnika
    	tabbedPane.add("P r i j a t e lj i", ((ModelListePrijateljaImplementacija)tabbedPane).new PodrazumevaniPanel());
    	
    	for(String prijatelj : prijatelji) {
    		tabbedPane.add(prijatelj, new TabbedPanePrijatelj(prijatelj, tabbedPane));    		
    	}
    	
    	// ukljucujemo tred koji osvezava sliku trenutno selektovanog prijatelja u glavnom prozoru
		osvezivac = new Osvezivac();
		osvezivacTred = new Thread(osvezivac);
		osvezivacTred.start();
		
		// budimo osvezivaca na dogadjaj promene taba
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        osvezivacTred.notify();
		      }
		    };
		tabbedPane.addChangeListener(changeListener);
    	
		
    }
    

    public class PodrazumevaniPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public PodrazumevaniPanel(){
                super();
                //podesavanja panela
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                
                //animacija omotana u panelu kako se nebi razvukla
                ImageIcon animacija = new ImageIcon( getClass().getResource("/resources/gui/animacija.gif"));
                JPanel omotac = new JPanel();

                omotac.add(new PanelZaSliku(animacija.getImage()));
                this.add(omotac);
                
                //Poruka dobrodoslice - stil i dodavanje na panel
                JLabel dobrodoslica = new JLabel("Dobrodosli u Codlex oko!");
                dobrodoslica.setAlignmentX(CENTER_ALIGNMENT);
                dobrodoslica.setFont(new Font(Font.SERIF, Font.BOLD, 30) );
                dobrodoslica.setForeground(Color.WHITE);
                this.add(dobrodoslica);
                
                //odvajanje od dna prozora
                this.add(new ZauzmiProstor(50));
        }               
    }
    
    // Graficki elemenat koji resava zauzima odredjeni prazan vertikalni prostor
    private class ZauzmiProstor extends JPanel{

		private static final long serialVersionUID = 1L;

			/**
             * @param height kolicina vertikalnog prostora koji zelimo da popunimo prazninom
             */
            public ZauzmiProstor(int height){

                    this.setSize(0, height);
                    this.setPreferredSize(new Dimension(0, height));
            }
    }
     
}
