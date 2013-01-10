package com.codlex.jsms.client.model;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.imgscalr.Scalr;

/**
 * 
 * Ova klasa reprezentuje jednog od prijatelja koji se posmatraju i sve metode za rad 
 * sa prijateljem su sadrzane u ovoj klasi.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */
public class TabbedPanePrijatelj extends JPanel  {
		private static final long serialVersionUID = 1L;
		//slika ekrana konkretnog prijatelja
        private BufferedImage ekran;
        // ova polja su samoobjasnjiva
        private String korisnickoIme;
        // referenca na tabbedpane u kome drzim sve studente
        private static JTabbedPane sadrzalacPrijatelja;
        
        public String getKorisnickoIme() {
        	return korisnickoIme;
        }
        
        public synchronized void setEkran(BufferedImage slikaEkrana) {
        	this.ekran = slikaEkrana;
        }
        
        static public void setSadrzalacStudenata(JTabbedPane sadrzalacStudenata) {
                TabbedPanePrijatelj.sadrzalacPrijatelja = sadrzalacStudenata;
        }
        
        public TabbedPanePrijatelj( String korisnickoIme, JTabbedPane sadrzalacStudenata){
                super();
                
                this.korisnickoIme = korisnickoIme;
                setSadrzalacStudenata(sadrzalacStudenata);                
                //dodajem studenta na jtabbedpane
                sadrzalacPrijatelja.add(korisnickoIme, this);       
                //novo konektovanog studenta stavljam kao selektovanog 
                //kako bih profesoru naglasio da se novi student konektovao
                sadrzalacPrijatelja.setSelectedComponent(this);
        }
        
        
        /**
         * 	Metoda osvezava sliku racunara studenta
         * 
         * */
        public synchronized void osvezi(){         
        	EventQueue.invokeLater( new Runnable() {
        		@Override
        		public void run() {
        			sadrzalacPrijatelja.repaint();
        		}
        	});
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
                
                if(ekran == null) {
                	try {
						ekran = ImageIO.read(getClass().getResource("/resources/no-image.png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                
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
                sadrzalacPrijatelja.remove(this);
        }

}
