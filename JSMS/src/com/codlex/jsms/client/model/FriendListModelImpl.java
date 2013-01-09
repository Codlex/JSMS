package com.codlex.jsms.client.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;

public class FriendListModelImpl extends JTabbedPane{
    private static JTabbedPane tabbedPane;
	public FriendListModelImpl(){
            super();
            this.setTabPlacement(FriendListModelImpl.LEFT);
    }       
    public static JTabbedPane getPane() {
    	return tabbedPane;
    }
    
    public static void createModel() {
    	Message m = CentralizedServerNIC.getNICService().getFriends();
    	Collection<String> friends = (Collection<String>) m.getMsgObject();
    	
    	tabbedPane = new FriendListModelImpl();
    	tabbedPane.add("F r i e n d s", ((FriendListModelImpl)tabbedPane).new PodrazumevaniPanel());
    	for(String friend : friends) {
    		tabbedPane.add(friend, new TabbedPaneFriend(friend, tabbedPane));    		
    	}
    }
    
    public class PodrazumevaniPanel extends JPanel {
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
                JLabel dobrodoslica = new JLabel("Dobrodosli u profesorsko oko!");
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
            /**
             * 
             * @param height kolicina vertikalnog prostora koji zelimo da popunimo prazninom
             */
            public ZauzmiProstor(int height){

                    this.setSize(0, height);
                    this.setPreferredSize(new Dimension(0, height));
            }
    }
    
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
    
    
}
