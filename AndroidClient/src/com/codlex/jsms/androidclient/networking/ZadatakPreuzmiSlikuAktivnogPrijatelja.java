package com.codlex.jsms.androidclient.networking;

import java.io.InputStream;

import com.codlex.jsms.client.model.Prijatelj;
import com.codlex.jsms.networking.Poruka;
import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Zadatak u pozadini komunicira sa mreznim slojem
 * i preuzima sliku aktivnog prijatelja
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */


public class ZadatakPreuzmiSlikuAktivnogPrijatelja extends AsyncTask<Prijatelj, Void, Bitmap>{

	@Override
	protected Bitmap doInBackground(Prijatelj... params) {
		Prijatelj aktivanPrijatelj = params[0];
		Bitmap novaSlika = null;
		
		// ucitavanje slike sa strima
		Poruka odgovor = getNICService().getEkran(aktivanPrijatelj.getKorisnickoIme());
		novaSlika = BitmapFactory.decodeStream( (InputStream) odgovor.getObjekatPoruke());
		
		return novaSlika;
	}
	

}
