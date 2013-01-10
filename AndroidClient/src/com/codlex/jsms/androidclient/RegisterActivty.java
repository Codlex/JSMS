package com.codlex.jsms.androidclient;


import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codlex.jsms.androidclient.networking.ZadatakNapraviNoviNalog;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;


/**
 * Aktivnost koja sluzi za kreiranje naloga.
 * Sastoji se iz tri polja za unos i dva dugmeta.
 * U polja za unos redom treba uneti
 * korisnicko ime, lozinku i email, a zatim pritisnuti
 * napravi nalog. Ukoliko je sve uspesno proslo pojavice se
 * korisnikov prostor.
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */

public class RegisterActivty extends Activity{

	// polja za korisnicko ime, lozinku i email;
	private EditText korisnickoIme,lozinka,email;
	// dugmici za nazad i za napravi nalog
	private Button dugmeNapraviNalog,dugmeNazad;
	// log radi testiranja unetih podataka
	private TextView log;
	
	/**
	 * 
	 * Metoda pri kreiranju nove aktivnosti.
	 * Sluzi da se inicijalizuju sve promenljive koje su potrebne
	 * za dalju funkcionalnost same aktivnosti.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// pozovemo metodu iz natklase prvo
		// a zatim postavimo layout koji zelimo da bude prikazan
		// u nasem slucaju register_layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		// vezujemo gui za nase komponente u klasi
		korisnickoIme = (EditText) findViewById(R.id.username2);
		lozinka = (EditText) findViewById(R.id.password2);
		email = (EditText) findViewById(R.id.email);
		dugmeNazad = (Button) findViewById(R.id.backb);
		dugmeNapraviNalog = (Button) findViewById(R.id.registerb2);
		log = (TextView) findViewById(R.id.log);
		
		// postavljamo listener za dugme nazad
		dugmeNazad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// postavljamo listener za dugme napravi nalog
		dugmeNapraviNalog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// pokupimo sta je uneseno u poljima za unos
				String korisnickoImee,lozinkaa,emailS;
				korisnickoImee = korisnickoIme.getText().toString();
				lozinkaa = lozinka.getText().toString();
				emailS = email.getText().toString();
				
				// napravimo zadatak koji ce probati da napravi nalog
				ZadatakNapraviNoviNalog zadatakNapraviNoviNalog = new ZadatakNapraviNoviNalog();
				Poruka odgovor = null;
				
				while(true){
					// zadatak ce pokusati da napravi novi nalog
					// i da nas prijavi na sistem
					zadatakNapraviNoviNalog.execute(korisnickoImee,lozinkaa,emailS);
				
					
					try {
						// rezultat zadatka upisujemo u odgovor
						odgovor = zadatakNapraviNoviNalog.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					if(odgovor != null)
						break;
					
				}
			
				// proveravamo da li je ishod bio uspesan ili neuspesan
				if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
					// ukoliko je uspesan pravimo novu aktivnost
					// dok smo sa ovom zavrsili koristeci finish() metodu
					Intent newActivity = new Intent("android.intent.action.KORISNIK");
					finish();
					// zapocinjemo novu aktivnost
					startActivity(newActivity);
				}
				else{
					// u suprotnom ispisujemo ovu poruku
					log.setText("Uhuhuh, Jambo ovde.");
				}				
			}
		});
		
	}
	
}
