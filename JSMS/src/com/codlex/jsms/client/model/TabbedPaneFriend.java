package com.codlex.jsms.client.model;

import java.awt.Color;
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
public class TabbedPaneFriend extends JPanel  {
        //slika ekrana konkretnog studenta
        private BufferedImage ekran;
        // ova polja su samoobjasnjiva
        private String username;
        // referenca na tabbedpane u kome drzim sve studente
        private static JTabbedPane sadrzalacStudenata;
        
        public String getUsername() {
        	return username;
        }
        
        public void setEkran(BufferedImage img) {
        	this.ekran = img;
        }
        
        static public void setSadrzalacStudenata(JTabbedPane sadrzalacStudenata) {
                TabbedPaneFriend.sadrzalacStudenata = sadrzalacStudenata;
        }
        
        
        public TabbedPaneFriend( String username, JTabbedPane s){
                super();
                
                //raspakovani podaci
                this.username = username;
                this.setSadrzalacStudenata(s);
                
                //uzimam jednu sliku ekrana
                this.osvezi();
                //dodajem studenta na jtabbedpane
                sadrzalacStudenata.add(username, this);
                
                
                //novo konektovanog studenta stavljam kao selektovanog 
                //kako bih profesoru naglasio da se novi student konektovao
                sadrzalacStudenata.setSelectedComponent(this);
                
        }
        
        
        /**
         * Metoda osvezava sliku racunara studenta
         * 
         * */
        public void osvezi(){         
                
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
                sadrzalacStudenata.remove(this);
                
        }

}
