package com.codlex.jsms.networking;

import java.io.Serializable;

/**
 * Poruka je objekat koji putuje od servera do klijenta putem TCP soketa, 
 * omogucava da se usklade zahtevi i odgovori gore navedenih strana.
 * 
 * @author Dejan Pekter RN 13/11 <dpekter11@raf.edu.rs>
 *
 */

public interface Poruka extends Serializable {
	/**
	 * Kod poruke predstavlja brzu identifikaciju poruke sa 
	 * malim memorijskim zahtevima. Koji omogucava optimizaciju 
	 * serijalizacije.
	 */
	public MSGCode getKodPoruke();
	
	/**
	 * Poruka cesto sa sobom nosi objekat potreban da bi se prenele
	 * sve informacije o trenutnom zahtevu ili odgovoru, 
	 * sto omogucava dosta generican pristup problemu.
	 * 
	 */
	public Object getObjekatPoruke();
}
