package com.codlex.jsms.androidclient.networking;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

import android.os.AsyncTask;

/**
 * Zadatak u pozadini komunicira sa mreznim slojem
 * i dodaje prijatelja
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */


public class ZadatakDodajPrijatelja extends AsyncTask<String, Void, Poruka>{

	@Override
	protected Poruka doInBackground(String... params) {
		String korisnickoIme = params[0];
		Poruka odgovor = CentralizovaniNIC.getNICService().dodajPrijatelja(korisnickoIme);
		return odgovor;
	}

}
