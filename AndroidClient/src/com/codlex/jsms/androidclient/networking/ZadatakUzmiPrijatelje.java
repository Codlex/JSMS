package com.codlex.jsms.androidclient.networking;

import java.util.ArrayList;
import java.util.Collection;

import android.os.AsyncTask;

import com.codlex.jsms.androidclient.model.OsnovniPrijatelj;
import com.codlex.jsms.client.model.Prijatelj;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

/**
 * Zadatak u pozadini komunicira sa mreznim slojem uz pomoc NIC-a
 * i preuzima listu prijatelja koju vraca kao rezultat nejgovor rada
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */


public class ZadatakUzmiPrijatelje extends AsyncTask<Void, Void, Collection<Prijatelj>>{

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<Prijatelj> doInBackground(Void... arg0) {
		Collection<String> korisnickaImena = new ArrayList<String>();
		Collection<Prijatelj> prijatelji = new ArrayList<Prijatelj>();
		
		
		Poruka odgovor = CentralizovaniNIC.getNICService().getPrijatelji();
		
		// preuzmemo korisnicka imena koja smo dobili od NIC-a pomocu metode
		// getPrijatelji
		if(odgovor.getKodPoruke() != MSGCode.USER_DOESNT_EXIST){
			 korisnickaImena = (Collection<String>) odgovor.getObjekatPoruke();
			 
			 // zatim prolazimo kroz imena i od njih pravimo prijatelje
			 // koje prosledjujemo kao rezultat
			 for(String korisnickoIme: korisnickaImena){
				 prijatelji.add(new OsnovniPrijatelj(korisnickoIme) );
			 }
			 return prijatelji;
		}
		return null;
	}


}
