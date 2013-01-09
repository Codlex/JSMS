package com.codlex.jsms.androidclient;


import java.util.concurrent.ExecutionException;

import com.codlex.jsms.androidclient.networking.ZadatakNapraviNoviNalog;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivty extends Activity{

	
	EditText korisnickoIme,lozinka,email;
	Button dugmeNapraviNalog,dugmeNazad;
	TextView log;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		korisnickoIme = (EditText) findViewById(R.id.username2);
		lozinka = (EditText) findViewById(R.id.password2);
		email = (EditText) findViewById(R.id.email);
		dugmeNazad = (Button) findViewById(R.id.backb);
		dugmeNapraviNalog = (Button) findViewById(R.id.registerb2);
		log = (TextView) findViewById(R.id.log);
		
		dugmeNazad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		dugmeNapraviNalog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String korisnickoImee,lozinkaa,emailS;
				korisnickoImee = korisnickoIme.getText().toString();
				lozinkaa = lozinka.getText().toString();
				emailS = email.getText().toString();
				
				ZadatakNapraviNoviNalog zadatakNapraviNoviNalog = new ZadatakNapraviNoviNalog();
				Poruka odgovor = null;
				
				while(true){
					
					zadatakNapraviNoviNalog.execute(korisnickoImee,lozinkaa,emailS);
					
					try {
						odgovor = zadatakNapraviNoviNalog.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					if(odgovor != null)
						break;
				}
			
				
				if(odgovor.getKodPoruke().equals(MSGCode.SUCCESS)){
					String token = (String) odgovor.getObjekatPoruke();
					Intent newActivity = new Intent("android.intent.action.USER");
					newActivity.putExtra("token", token);
					newActivity.putExtra("username",korisnickoImee);
					finish();
					startActivity(newActivity);
				}
				else{
					log.setText("Uhuhuh, Jambo ovde.");
				}				
			}
		});
		
	}
	
}
