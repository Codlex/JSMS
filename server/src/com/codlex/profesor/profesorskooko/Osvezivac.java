package com.codlex.profesor.profesorskooko;

import javax.swing.JTabbedPane;
/**
 * Ova klasa na odredjen vremenski interval osvezava sliku trenutno aktivnog ekrana studenta.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */
public class Osvezivac implements Runnable {
	
	//promenljiva koja sluzi za zaustavljanje osvezavanja
	private boolean stani = false;
	
	//sadrzalac studenata
	private JTabbedPane tabla;
	
	public Osvezivac(JTabbedPane tabla){
		this.tabla = tabla;		
	}
	
	public void setStani(boolean stop) {
		this.stani = stop;
	}
	
	@Override
	public void run() {
		
		//dokle god se ne zaustavi refresuje trenutno aktivnog studenta
		while(!stani){
			//ukoliko nema studenata konektovanih (otvoren podrazumevani tab)
			//onda samo preskocim osvezavanje ovaj put
			if(!(tabla.getSelectedComponent() instanceof Student))
				continue;
			else //osvezavam trenutno selektovanog studenta
				((Student) tabla.getSelectedComponent()).osvezi();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// ignorisem ovaj exception jer se verovatno nece desiti
				// ne znam pametnu reakciju na njega
			}
		}
		
	}

}
