package com.codlex.student.profesorskooko;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;


/**
 * SredomaEkrana je klasa koja za datu sirinu i duzinu prozora koji zelimo da pozicioniramo vraca Rectangle tako da se takav prozor postavi u sredinu ekrana.
 */
class SredinaEkrana extends Rectangle{
	
	/**
	 * @param sirina sirina ekrana koji zelimo da postavimo na sredinu
	 * @param visina visina ekrana koji zelimo da postavimo na sredinu
	 */
	public SredinaEkrana(int sirina, int visina){
		
		Dimension dimenzijeEkrana = Toolkit.getDefaultToolkit().getScreenSize();
		
		// koliko cu da odvojim ekran od gornje odnosno leve ivice ekrana
		int gore = (dimenzijeEkrana.height - visina) / 2;
		int levo = (dimenzijeEkrana.width - sirina) / 2;
		
		this.x = levo;
		this.y = gore;
		this.height = visina;
		this.width = sirina;
		
	}
}