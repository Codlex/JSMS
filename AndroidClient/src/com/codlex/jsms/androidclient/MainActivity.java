package com.codlex.jsms.androidclient;


import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codlex.jsms.androidclient.networking.ZadatakPrijaviKorisnika;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

/**
 * 
 * Glavna tj. pocetna aktivnost android aplikacije
 * na kojoj korisnik moze da se uloguje ili da napravi novi nalog.
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>  
 * 
 */

public class MainActivity extends Activity {
	
	// dugmici za prijavu i pravljenje naloga
	private Button dugmePrijaviSe,dugmeNapraviNalog;
	// polja za unosenje korisnickog imena i lozinke
	private EditText korisnickoIme,lozinka;
	// poruka o ispisu o pogresnom unetom korisnickom imenu ili lozinci
	private TextView pogresnoKorisnickoImeIliLozinka;

	
	/**
	 * 
	 * Metoda pri kreiranju nove aktivnosti.
	 * Sluzi da se inicijalizuju sve promenljive koje su potrebne
	 * za dalju funkcionalnost same aktivnosti.
	 * 
	 */
    @Override
    protected void onCreate(Bundle zapamcenoStanjeInstance) {
    	// pozovemo metodu iz natklase prvo
    	// a zatim postavimo layout koji zelimo da bude prikazan
    	// u nasem slucaju activity_main
        super.onCreate(zapamcenoStanjeInstance);
        setContentView(R.layout.activity_main);
        
        // podesavanje tj. povezivanje gui komponenti sa nasim klasnim
        // komponentama
        dugmePrijaviSe = (Button) findViewById(R.id.loginb);
        dugmeNapraviNalog = (Button) findViewById(R.id.registerb);
        korisnickoIme = (EditText) findViewById(R.id.username);
        lozinka = (EditText) findViewById(R.id.password);
        pogresnoKorisnickoImeIliLozinka = (TextView) findViewById(R.id.wa);
        
        // postavljamo osluskivac na dugme za prijavu na sistem
        dugmePrijaviSe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				// Uzimamo korisnicko ime i lozinku iz polja
				String korisnickoImee,lozinkaa;
				korisnickoImee = korisnickoIme.getText().toString();
				lozinkaa = lozinka.getText().toString();
				
				// napravimo zadatak koji ce nas prijaviti na sistem pomocu NIC-a
				ZadatakPrijaviKorisnika zadatakPrijaviKorisnika = new ZadatakPrijaviKorisnika();
				// inicijalizujemo odgovor na null
				Poruka odgovor = null;
				
				// dok zadatak ne zavrsi svoju metodu
				while(true){
					
			
					// zadatak pokusava da nas prijavi na sistem
					zadatakPrijaviKorisnika.execute(korisnickoImee,lozinkaa);
				
					try {
						
						odgovor = zadatakPrijaviKorisnika.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					
					// provera da li je zadatak gotov
					if(odgovor != null)
						break;
				 
				}
			
				// proveravamo da li je poruka SUCCESS
				// ukoliko jeste uspesno smo prijavljeni na sistem
				// pravimo novu aktivnost i zapocinjemo je
				if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
					Intent noviAktiviti = new Intent("android.intent.action.KORISNIK");
					isprazniSvaPolja();
					startActivity(noviAktiviti);
				}
				else{
					// u suprotnom ispisujemo poruku o greshci
					pogresnoKorisnickoImeIliLozinka.setText("Pogresno korisnicko ime ili lozinka");
				}
			}
		});
        
        // postavljamo osluskivac na dugme za prijavu na sistem
        dugmeNapraviNalog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// brisemo polja ukoliko je nesto bilo u njima
				isprazniSvaPolja();
				// zapocinjemo novu aktivnost za kreiranje naloga na sistemu
				startActivity(new Intent("android.intent.action.NAPRAVI_NALOG"));				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * 
     * Metoda koja cisti polja za korisnicko ime, lozinku i poruku gresci
     * 
     */
    private void isprazniSvaPolja(){
    	korisnickoIme.setText("");
    	lozinka.setText("");
    	pogresnoKorisnickoImeIliLozinka.setText("");
    }

}
