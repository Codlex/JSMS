package com.codlex.jsms.androidclient;


import java.util.concurrent.ExecutionException;
import com.codlex.jsms.androidclient.networking.ZadatakPrijaviKorisnika;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * Glavna tj. pocetna aktivnost android aplikacije
 * na kojoj korisnik moze da se uloguje ili da napravi novi nalog.
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>  
 * 
 */

public class MainActivity extends Activity {

	private Button dugmePrijaviSe,dugmeNapraviNalog;
	private EditText korisnickoIme,lozinka;
	private TextView pogresnoKorisnickoImeIliLozinka;
	
    @Override
    protected void onCreate(Bundle zapamcenoStanjeInstance) {
        super.onCreate(zapamcenoStanjeInstance);
        setContentView(R.layout.activity_main);
        
        dugmePrijaviSe = (Button) findViewById(R.id.loginb);
        dugmeNapraviNalog = (Button) findViewById(R.id.registerb);
        korisnickoIme = (EditText) findViewById(R.id.username);
        lozinka = (EditText) findViewById(R.id.password);
        pogresnoKorisnickoImeIliLozinka = (TextView) findViewById(R.id.wa);
        
        dugmePrijaviSe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				// Uzimamo korisnicko ime i lozinku iz polja
				String korisnickoImee,lozinkaa;
				korisnickoImee = korisnickoIme.getText().toString();
				lozinkaa = lozinka.getText().toString();
				
				// napravimo task i response
				ZadatakPrijaviKorisnika zadatakPrijaviKorisnika = new ZadatakPrijaviKorisnika();
				Poruka odgovor = null;
				
				while(true){
					zadatakPrijaviKorisnika.execute(korisnickoImee,lozinkaa);
				
				// pokupimo odgovor
					try {
						odgovor = zadatakPrijaviKorisnika.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				 if(odgovor != null) break;
				}
			
				if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
					String token = (String) odgovor.getObjekatPoruke();
					Intent noviAktiviti = new Intent("android.intent.action.KORISNIK");
					noviAktiviti.putExtra("token", token);
					noviAktiviti.putExtra("korisnickoIme", korisnickoImee);
					makeFieldsBlank();
					startActivity(noviAktiviti);
				}
				else{
					pogresnoKorisnickoImeIliLozinka.setText("Pogresno korisnicko ime ili lozinka");
				}
			}
		});
        
        dugmeNapraviNalog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeFieldsBlank();
				startActivity(new Intent("android.intent.action.NAPRAVI_NALOG"));				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void makeFieldsBlank(){
    	korisnickoIme.setText("");
    	lozinka.setText("");
    }
    
}
