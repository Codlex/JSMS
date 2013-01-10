package com.codlex.jsms.androidclient;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codlex.jsms.androidclient.model.AndroidModelListePrijatelja;
import com.codlex.jsms.androidclient.model.OsnovniPrijatelj;
import com.codlex.jsms.androidclient.networking.ZadatakPreuzmiSlikuAktivnogPrijatelja;
import com.codlex.jsms.client.model.Prijatelj;
import com.codlex.jsms.networking.Korisnik;

public class UserActivity extends Activity{
	
	// Friend model
	AndroidModelListePrijatelja androidModelListePrijatelja;
	
	ListView lista;
	ArrayList<View> listaPrijatelja;
	String token,korisnickoIme;
	TextView porukaZdravo;
	Button dugmeIzlogujSe,dugmeDodajPrijatelja;
	
	// addFriendDialog components
	TextView pogresnoKorisnickoIme;
	Button dugmeNazad2,dugmeDodajPrijatelja2;
	EditText trazenoKorisnickoIme;
	
	// layouti za dialog boxove
	View dialogLayoutDodajPrijatelja;
	View dialogLayoutPrikazEkrana;
	
	// friends lista
	List<String> prijatelji;

	
	
	// viewScreenDialog components
	Button dugmeNazad3;
	ImageView slikaKorisnika;
	
	// Dialogs
	AlertDialog alertDialogDodajPrijatelja;
	AlertDialog alertDialogPrikazEkrana;
	//AlertDialog 
	// builder za addFriendDialog
	AlertDialog.Builder dodajPrijateljaBuilder;
	// builder za view screen
	AlertDialog.Builder prikazEkranaBuilder;
	
	
	@Override
	protected void onCreate(Bundle zapamcenoStanjeInstance) {
		
		// TODO Auto-generated method stub
		super.onCreate(zapamcenoStanjeInstance);
		setContentView(R.layout.user_layout);
		
		
		// inicijalizacija add friend builder-a
		dodajPrijateljaBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		// incijalizacija screen view builder-a
		prikazEkranaBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater2 = getLayoutInflater();

		// inzijalizacija modela
		androidModelListePrijatelja = new AndroidModelListePrijatelja();
		
		// lista frendova
		prijatelji = new ArrayList<String>();
		prijatelji = (ArrayList<String>) androidModelListePrijatelja.getKorisnickaImenaPrijatelja();
		
		// prosledjen token
		Bundle extras = getIntent().getExtras();
		token = extras.getString("token");
		// prosledjen username
		korisnickoIme = extras.getString("korisnickoIme");
		
		
		
		// podesimo hello :)
		porukaZdravo = (TextView) findViewById(R.id.hello);
		porukaZdravo.setText("Hello " + korisnickoIme + "!");
		
		// logout dugme
		dugmeIzlogujSe = (Button) findViewById(R.id.logoutb);
		dugmeIzlogujSe.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		// addfriend dugme
		dugmeDodajPrijatelja2 = (Button) findViewById(R.id.addfriendb);
		dugmeDodajPrijatelja2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// dialog za dodavanje prijatelja
				dialogLayoutDodajPrijatelja = inflater.inflate(R.layout.add_friend_dialog_layout, (ViewGroup) getCurrentFocus(),false);

				dodajPrijateljaBuilder.setView(dialogLayoutDodajPrijatelja);
				dodajPrijateljaBuilder.setTitle("Dodaj prijatelja, uhuh!");
				alertDialogDodajPrijatelja = dodajPrijateljaBuilder.create();
				alertDialogDodajPrijatelja.show();
				inicijalizujDodajPrijateljaDialog();
			
			}
		});
		
		// lista prijatelja
		lista = (ListView) findViewById(R.id.listView1);
		lista.setAdapter(new ArrayAdapter<String>(this, R.layout.item,prijatelji));
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String korisnickoImeSelektovano = ((TextView)arg1).getText().toString();
				
				androidModelListePrijatelja.setAktivnogPrijatelja((OsnovniPrijatelj) androidModelListePrijatelja.getPrijatelj(korisnickoImeSelektovano));
				
				dialogLayoutPrikazEkrana = inflater2.inflate(R.layout.screen_vew_layout, (ViewGroup) getCurrentFocus(), false);
				prikazEkranaBuilder.setView(dialogLayoutPrikazEkrana);
				prikazEkranaBuilder.setTitle("Friend screen");
				alertDialogPrikazEkrana = prikazEkranaBuilder.create();
				alertDialogPrikazEkrana.show();
				inicijalizujPrikazEkranaDialog();
				
				
				Thread osvezavanjeSlike = new Thread(){
					
					@Override
					public void run() {
						slikaKorisnika = (ImageView)alertDialogPrikazEkrana.findViewById(R.id.friendimage); 

						
						Thread invalidate = new Thread() {
							public void run() {
								  OsnovniPrijatelj prijatelj = androidModelListePrijatelja.getAktivnogPrijatelja(); 
								  if(prijatelj != null) {
									  slikaKorisnika.setImageBitmap(prijatelj.getEkranBitmap());
									  slikaKorisnika.invalidate();
								  }
								}
						};
						while(alertDialogPrikazEkrana.isShowing() == true){
							ZadatakPreuzmiSlikuAktivnogPrijatelja zadatakPreuzmiSlikuAKtivnogPrijatelja = new ZadatakPreuzmiSlikuAktivnogPrijatelja();
							zadatakPreuzmiSlikuAKtivnogPrijatelja.execute(androidModelListePrijatelja.getAktivnogPrijatelja());
							 try {
								 Bitmap stariBitmap = androidModelListePrijatelja.getAktivnogPrijatelja().getEkranBitmap();
								 
								(androidModelListePrijatelja.getAktivnogPrijatelja()).setEkranBitmap(zadatakPreuzmiSlikuAKtivnogPrijatelja.get());
								if( stariBitmap != null) {
									 stariBitmap.recycle();
								 }
								runOnUiThread( invalidate );
								Thread.sleep(500);
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
					}
					
				};
				osvezavanjeSlike.start();				
			}
		});
	}

	
	void inicijalizujPrikazEkranaDialog(){
		dugmeNazad3 = (Button) alertDialogPrikazEkrana.findViewById(R.id.backb3);
		
		dugmeNazad3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialogPrikazEkrana.cancel();
				androidModelListePrijatelja.setAktivnogPrijatelja(null);
			}
		});
	}
	
	void inicijalizujDodajPrijateljaDialog(){
		
		// dugmici
		dugmeNazad2	= (Button) alertDialogDodajPrijatelja.findViewById(R.id.backb2);
		dugmeDodajPrijatelja2	= (Button) alertDialogDodajPrijatelja.findViewById(R.id.add);
		trazenoKorisnickoIme = (EditText) alertDialogDodajPrijatelja.findViewById(R.id.findUsername);
		pogresnoKorisnickoIme = (TextView) alertDialogDodajPrijatelja.findViewById(R.id.wa2);
	
		// listeneri
		dugmeNazad2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				alertDialogDodajPrijatelja.cancel();
			}
		});
		dugmeDodajPrijatelja2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String korisnickoIme = trazenoKorisnickoIme.getText().toString();
				if(androidModelListePrijatelja.dodajPrijatelja(korisnickoIme) == true){
					prijatelji.add(korisnickoIme);
					lista.invalidateViews();
					Toast friendAdded = Toast.makeText(getApplicationContext(), "Prijatelj uspesno dodat!", Toast.LENGTH_SHORT);
					friendAdded.show();
					
				}
				else{
					Toast friendNotAdded = Toast.makeText(getApplicationContext(), "Pogresno uneto korisnicko ime.", Toast.LENGTH_SHORT);
					friendNotAdded.show();
				}
				// isprazni polje za findUsername
				trazenoKorisnickoIme.setText("");			
			}
		});
	
	}
	

	
}
