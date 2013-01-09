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
	private Collection<Prijatelj> prijatelji;
	private OsnovniPrijatelj aktivanPrijatelj;
	
	public AndroidModelListePrijatelja() {
		// pravimo novu listu prijatelja
		this.prijatelji = new ArrayList<Prijatelj>();
		this. aktivanPrijatelj = null;
		// uzima prijatelje
		// pravimo zadatak koji ce u pozadini otvoriti konekciju
		// sa serverom prijateljstva i uzeti listu prijatelja
		ZadatakUzmiPrijatelje zadatakUzmiPrijatelje = new ZadatakUzmiPrijatelje();
		zadatakUzmiPrijatelje.execute();
		try {
			this.prijatelji = zadatakUzmiPrijatelje.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	

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
		for(Prijatelj prijatelj:prijatelji){
			korisnickaImenaPrijatelja.add(prijatelj.getKorisnickoIme());
		}
		return korisnickaImenaPrijatelja;
	}
	
	@Override
	public Prijatelj getPrijatelj(String korisnickoIme) {

		for(Prijatelj prijatelj: prijatelji){
			if(korisnickoIme.equals(prijatelj.getKorisnickoIme()))
				return prijatelj;
		}
	
		return null;
	}

	@Override
	public boolean dodajPrijatelja(String korisnickoIme) {
		
		// task za dodavanje prijatelja
		ZadatakDodajPrijatelja addFriendTask = new ZadatakDodajPrijatelja();
		addFriendTask.execute(korisnickoIme);
		
		Poruka odgovor = null;
		
		try {
			odgovor = addFriendTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
			prijatelji.add(new OsnovniPrijatelj(korisnickoIme));
			return true;
		}
		return false;		
	}
	
	public void setAktivnogPrijatelja(OsnovniPrijatelj prijatelj){
		aktivanPrijatelj = prijatelj;
	}
	
	public OsnovniPrijatelj getAktivnogPrijatelja(){
		return aktivanPrijatelj;
	}

}
