package com.codlex.student.profesorskooko;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Klasa panel za sliku omogucava omotavanje slike unutar panela kako bi se mogla lagano dodati na drugi panel ili frame.
 */

class PanelZaSliku extends JPanel{
		
		private Image slika;
		
		/**
		 * Konstruktor podesava sliku i velicinu panela postavlja na velicinu slike.
		 *
		 * @param slika slika koju zelimo da umotamo
		 */
		public PanelZaSliku(Image slika){
			super();
			
			this.slika = slika;
			this.setPreferredSize(new Dimension(slika.getWidth(this), slika.getHeight(this)));			
		}
		
		/**
		 * Iscrtavam sliku u njenoj velicini
		 * 
		 * */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(slika, 0, 0, this.getWidth(), slika.getHeight(this), this);
		}

}