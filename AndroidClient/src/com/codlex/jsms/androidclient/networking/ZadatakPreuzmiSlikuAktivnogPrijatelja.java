package com.codlex.jsms.androidclient.networking;

import java.io.IOException;
import java.io.InputStream;

import com.codlex.jsms.client.model.Prijatelj;
import com.codlex.jsms.networking.Poruka;
import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Zadatak u pozadini komunicira sa mreznim slojem uz pomoc NIC-a
 * i preuzima sliku aktivnog prijatelja
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */


public class ZadatakPreuzmiSlikuAktivnogPrijatelja extends AsyncTask<Prijatelj, Void, Bitmap>{

	@Override
	protected Bitmap doInBackground(Prijatelj... params) {
		// kao parametar se prosledjuje aktivan prijatelj
		Prijatelj aktivanPrijatelj = params[0];
		// postavimo da je nova slika null na pocetku
		// da bi zdatak preko NIC-a dobio sliku i to prosledio
		// kao rezultat
		Bitmap novaSlika = null;
		
		// ucitavanje slike sa strima
		Poruka odgovor = getNICService().getEkran(aktivanPrijatelj.getKorisnickoIme());
		InputStream ulaz = (InputStream) odgovor.getObjekatPoruke();
		try {
			novaSlika = BitmapFactory.decodeStream( ulaz );
		} finally {	
			try {
				ulaz.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return novaSlika;
	}
	

}
