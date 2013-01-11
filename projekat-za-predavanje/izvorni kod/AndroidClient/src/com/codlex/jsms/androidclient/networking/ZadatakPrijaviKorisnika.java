package com.codlex.jsms.androidclient.networking;

import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.getNICService;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

import android.os.AsyncTask;


/**
 * Zadatak u pozadini komunicira sa mreznim slojem uz pomoc NIC-a
 * i prijavi korisnika na sistem
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */


public class ZadatakPrijaviKorisnika extends AsyncTask<String, Void, Poruka>{
	


	@Override
	protected Poruka doInBackground(String... params) {
		
		// kao parametre zadatku prosledjujemo
		// korisnicko ime i lozinku korsnika koga zelimo da prijavimo
		// na sistem pomocu NIC-a
		String korisnickoIme = params[0];
		String lozinka = params[1];
		
		Korisnik korisnik = new OsnovniKorisnik(korisnickoIme,lozinka);
		Poruka odgovor = getNICService().prijaviSe(korisnik);
		// NIC vraca poruku o uspehu ili neuspehu date operacije
		return odgovor;
	}

}
