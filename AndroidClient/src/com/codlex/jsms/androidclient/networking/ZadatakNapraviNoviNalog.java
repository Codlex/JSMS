package com.codlex.jsms.androidclient.networking;

import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.getNICService;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

import android.os.AsyncTask;

/**
 * Zadatak u pozadini komunicira sa mreznim slojem uz pomoc NIC-a
 * i pravi novi nalog
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */

public class ZadatakNapraviNoviNalog extends AsyncTask<String, Void, Poruka>{

	@Override
	protected Poruka doInBackground(String... params) {
		// kao parametre zadatu prosledjujemo
		// korisnicko ime, lozinku i email
		// zatim on pokusa da napravi novi nalog
		// i vraca odgovor o uspehu ili neuspehu
		String korisnickoIme = params[0];
		String lozinka = params[1];
		String email 	= params[2];
		
		Korisnik korisnik = new OsnovniKorisnik(korisnickoIme,lozinka,email);
		Poruka odgovor = getNICService().napraviNalog(korisnik);
		
		return odgovor;
	}

}
