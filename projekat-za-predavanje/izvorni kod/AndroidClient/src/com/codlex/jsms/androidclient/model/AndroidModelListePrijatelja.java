package com.codlex.jsms.androidclient.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import com.codlex.jsms.androidclient.networking.ZadatakDodajPrijatelja;
import com.codlex.jsms.androidclient.networking.ZadatakUzmiPrijatelje;
import com.codlex.jsms.client.model.Prijatelj;
import com.codlex.jsms.client.model.ModelLIstePrijatelja;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;


/**
 * Ova klasa sadrzi metode za rad sa listom prijatelja koju ima korisnik kao i ako je aktiviran
 * prikaz slike prijatelja, aktivnog prijatelja
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */




public class AndroidModelListePrijatelja implements ModelLIstePrijatelja{
	// lista prijatelja i aktivan prijatelj
	private Collection<Prijatelj> prijatelji;
	private OsnovniPrijatelj aktivanPrijatelj;
	
	// konstruktor
	public AndroidModelListePrijatelja() {
		// pravimo novu listu prijatelja
		// postavljamo aktivnog prijatelja na null kao podrazumevanu vrednost
		this.prijatelji = new ArrayList<Prijatelj>();
		this. aktivanPrijatelj = null;

		// pravimo zadatak koji ce u pozadini otvoriti konekciju
		// sa serverom prijateljstva preko NIC-a i uzeti listu prijatelja
		ZadatakUzmiPrijatelje zadatakUzmiPrijatelje = new ZadatakUzmiPrijatelje();
		zadatakUzmiPrijatelje.execute();
		try {
		// nakon sto se zavrsi nas zadatak, rezultat mozemo dobiti
		// pomocu metode get() i rezultat smestamo
		// u nasu listu prijatelja
			this.prijatelji = zadatakUzmiPrijatelje.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Metoda vraca listu prijatelja koju sadrzi model za datog korisnika
	 * 
	 */
	
	@Override
	public Collection<Prijatelj> getPrijatelji() {
		return prijatelji;
	}
	/**
	 * Metoda vraca listu stringova korisnickih imena prijatelja
	 * 
	 */
	public Collection<String> getKorisnickaImenaPrijatelja(){
		Collection<String> korisnickaImenaPrijatelja = new ArrayList<String>();
		// prolazimo kroz sve prijatelje i pokupimo njihova korisnicka imena
		for(Prijatelj prijatelj:prijatelji){
			korisnickaImenaPrijatelja.add(prijatelj.getKorisnickoIme());
		}
		return korisnickaImenaPrijatelja;
	}
	
	/**
	 * Metoda pokusa da pronadje prijatelja po korisnickom imenu koje se prosledjuje
	 * kao parametar, ukoliko ga pronadje vraca ga u suprotnom vraca null
	 */
	
	@Override
	public Prijatelj getPrijatelj(String korisnickoIme) {
		
		// prolazimo kroz sve prijatelje
		for(Prijatelj prijatelj: prijatelji){
			if(korisnickoIme.equals(prijatelj.getKorisnickoIme()))
			// pronasli smo trazenog prijatelja
				return prijatelj;
		}
		// prijatelj sa datim korisnickim imenom nije pronadjen
		return null;
	}
	
	/**
	 * Metoda pokusa da doda prijatelja sa zadatim korisnickim imenom
	 * tako sto komunicira sa serverom prijateljstva preko mreznog sloja
	 * uz pomoc NIC-a.Ukoliko uspe da ga doda, ona ga ujedno doda i kod sebe
	 * u listu prijatelja.
	 * 
	 */
	
	@Override
	public boolean dodajPrijatelja(String korisnickoIme) {
		
		// zadatak za dodavanje prijatelja
		ZadatakDodajPrijatelja addFriendTask = new ZadatakDodajPrijatelja();
		addFriendTask.execute(korisnickoIme);
		
		// postavimo odgovor na null
		Poruka odgovor = null;
		
		try {
			// pokupimo rezultat naseg zadatka koji se izvrsio
			// koji je tipa Poruke
			odgovor = addFriendTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		// proverimo da li smo uspesno dodali prijatelja na serveru
		// ukoliko jesmo dodajemo ga i u nasoj listi
		// i vracamo true, u suprotnom vracamo false
		if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
			prijatelji.add(new OsnovniPrijatelj(korisnickoIme));
			return true;
		}
		return false;		
	}
	
	/**
	 * Metoda postavlja aktivnog prijatelja
	 */
	public void setAktivnogPrijatelja(OsnovniPrijatelj prijatelj){
		aktivanPrijatelj = prijatelj;
	}
	
	/**
	 * Metoda vraca aktivnog prijatelja
	 */
	public OsnovniPrijatelj getAktivnogPrijatelja(){
		return aktivanPrijatelj;
	}

}
