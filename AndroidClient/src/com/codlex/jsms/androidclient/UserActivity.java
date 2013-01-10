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
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;

/**
 * Aktivnost koja predstavlja korisnikov prostor kada se uloguje na sistem
 * Ima pregled svojih prijatelja kao i opciju da doda nove.
 * Takodje ima opciju da se izloguje.
 * 
 * @author Milos Biljanovic RN 21/11 <mbiljanovic11@raf.edu.rs>
 *
 */

public class UserActivity extends Activity{
	
	// model prijatelja
	private AndroidModelListePrijatelja androidModelListePrijatelja;
	
	// komponente za aktivnost
	private ListView lista;
	private String korisnickoIme;
	private TextView porukaZdravo;
	Button dugmeIzlogujSe,dugmeDodajPrijatelja;
	
	// komponente za dodajPrijateljaDialog
	private Button dugmeNazad2,dugmeDodajPrijatelja2;
	private EditText trazenoKorisnickoIme;
	
	// komponente za prikazEkranaDialog
	private Button dugmeNazad3;
	private ImageView slikaKorisnika;
	
	// layout za dialoge DodajPrijatelja i PrikazEkrana
	private View dialogLayoutDodajPrijatelja;
	private View dialogLayoutPrikazEkrana;
	
	// lista prijatelja
	private List<String> prijatelji;
	
	// Dialozi
	private AlertDialog alertDialogDodajPrijatelja;
	private AlertDialog alertDialogPrikazEkrana;
	
	// Builderi za dialoge
	// builder za addFriendDialog
	private AlertDialog.Builder dodajPrijateljaBuilder;
	// builder za view screen
	private AlertDialog.Builder prikazEkranaBuilder;
	
	
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
		// u nasem slucaju user_layout
		super.onCreate(zapamcenoStanjeInstance);
		setContentView(R.layout.user_layout);
		
		
		// inicijalizacija dodajPrijatelja builder-a
		// i pravljenje layoutInflater-a
		dodajPrijateljaBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		// incijalizacija  prikazEkrana builder-a
		// i pravljenje layoutInflater-a
		prikazEkranaBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater2 = getLayoutInflater();

		// inzijalizacija modela
		androidModelListePrijatelja = new AndroidModelListePrijatelja();
		
		// lista frendova
		prijatelji = new ArrayList<String>();
		
		prijatelji = (ArrayList<String>) androidModelListePrijatelja.getKorisnickaImenaPrijatelja();
		prijatelji.add(0, "LISTA PRIJATELJA");
		// dodavanje podrazumevanog prijatelja
		
		
		
		// zapamtimo u korisnicko ime, ime trenutnog ulogovanog korisnika
		// kako bi ga ispisali na ekranu
		korisnickoIme = CentralizovaniNIC.getNICService().getTrenutnoUlogovanKorisnik().getKorisnickoIme();
		
		
		
		// podesimo hello :)
		porukaZdravo = (TextView) findViewById(R.id.hello);
		porukaZdravo.setText("Zdravo " + korisnickoIme + "!");
		
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
				dodajPrijateljaBuilder.setTitle("Dodaj prijatelja, uhuhuh!");
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
				// nepostojeci podrazumevani prijatelj
				if(korisnickoImeSelektovano.equals("LISTA PRIJATELJA"))
					return;
				
				// u suprotnom zeli da prikaze ekran nekog svog prijatelja
				// postavljamo aktivnog prijatelja na osnovu korisnickog imena
				// koje je selektovano
				
				androidModelListePrijatelja.setAktivnogPrijatelja((OsnovniPrijatelj) androidModelListePrijatelja.getPrijatelj(korisnickoImeSelektovano));
				
				// postavljamo osnovna podeshavanja za alertDialog
				dialogLayoutPrikazEkrana = inflater2.inflate(R.layout.screen_vew_layout, (ViewGroup) getCurrentFocus(), false);
				prikazEkranaBuilder.setView(dialogLayoutPrikazEkrana);
				prikazEkranaBuilder.setTitle("SLIKA PRIJATELJA");
				// pravimo alertDialogPrikazEkrana pomocu buildera
				alertDialogPrikazEkrana = prikazEkranaBuilder.create();
				// prikazujemo dialogPrikazEkrana;
				alertDialogPrikazEkrana.show();
				// inicijalizujemo Dialog za prikaz ekrana
				inicijalizujPrikazEkranaDialog();
				
				// pravimo tred za osvezavanje slike na svakih 500ms
				Thread osvezavanjeSlike = new Thread(){
					
					@Override
					public void run() {
						// povezemo sliku korisnika sa identifikatorom koji pripada prikazu ekrana
						slikaKorisnika = (ImageView)alertDialogPrikazEkrana.findViewById(R.id.friendimage); 

						// potreban je josh jedan tred kako bi mogli da pristupamo
						// UI elementima kao sto je slika prijatelja koju zelimo da izmenimo
						Thread invalidate = new Thread() {
							public void run() {
								  // uzmemo aktivnog prijatelja
								  OsnovniPrijatelj prijatelj = androidModelListePrijatelja.getAktivnogPrijatelja(); 
								  if(prijatelj != null) {
									  // pokupimo "novu" sliku prijatelja
									  // i postavimo je na sliku korisnika
									  // pomoc setImageBitmap i metode invalidate
									  slikaKorisnika.setImageBitmap(prijatelj.getEkranBitmap());
									  slikaKorisnika.invalidate();
								  }
								}
						};
						// pozivamo isti task da osvezava nasu sliku prijatelja
						// sve dok korisnik ne ugasi dialog
						while(alertDialogPrikazEkrana.isShowing() == true){
							ZadatakPreuzmiSlikuAktivnogPrijatelja zadatakPreuzmiSlikuAKtivnogPrijatelja = new ZadatakPreuzmiSlikuAktivnogPrijatelja();
							// pokusavamo da dobijemo sliku
								zadatakPreuzmiSlikuAKtivnogPrijatelja.execute(androidModelListePrijatelja.getAktivnogPrijatelja());
							
							 try {
								 // takodje vrsimo recikliranje starog bitmapa kako ne bi doslo
								 // do nagomilavanja i overflowa u memoriji
								 Bitmap stariBitmap = androidModelListePrijatelja.getAktivnogPrijatelja().getEkranBitmap();
								 
								(androidModelListePrijatelja.getAktivnogPrijatelja()).setEkranBitmap(zadatakPreuzmiSlikuAKtivnogPrijatelja.get());
								if( stariBitmap != null) {
									 stariBitmap.recycle();
								 }
								
								// pokrecemo tred koji ce osveziti sliku
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
				// startujemo prvi tred za osvezavanje slike
				osvezavanjeSlike.start();				
			}
		});
	}

	/**
	 * 
	 * Metoda za inicijalizaciju Dialoga za prikaz ekrana
	 * 
	 */
	private void inicijalizujPrikazEkranaDialog(){
		// dugme nazad
		dugmeNazad3 = (Button) alertDialogPrikazEkrana.findViewById(R.id.backb3);
		// osluskivac za dugme nazad
		dugmeNazad3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialogPrikazEkrana.cancel();
				androidModelListePrijatelja.setAktivnogPrijatelja(null);
			}
		});
	}
	
	/**
	 * 
	 * Metoda za inicijalizaciju Dialoga za dodavanje prijatelja
	 * 
	 */
	private void inicijalizujDodajPrijateljaDialog(){
		
		// dugmici
		dugmeNazad2	= (Button) alertDialogDodajPrijatelja.findViewById(R.id.backb2);
		dugmeDodajPrijatelja2	= (Button) alertDialogDodajPrijatelja.findViewById(R.id.add);
		// uneto korisnicko ime
		trazenoKorisnickoIme = (EditText) alertDialogDodajPrijatelja.findViewById(R.id.findUsername);

		// listeneri za osluskivanje eventova na dugmetu nazad i dodaj prijatelja
		dugmeNazad2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				alertDialogDodajPrijatelja.cancel();
			}
		});
		dugmeDodajPrijatelja2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// uzmemo korisnicko ime koje je korisnik uneo
				String korisnickoIme = trazenoKorisnickoIme.getText().toString();
				// probamo da ga dodamo
				if(androidModelListePrijatelja.dodajPrijatelja(korisnickoIme) == true){
					// ako je uspesno, dodamo ga i u nasu listu
					// i takodje refreshujemo nasu listu kako bi promena
					// bila vidljiva
					prijatelji.add(korisnickoIme);
					lista.invalidateViews();
					// ispisujemo poruku o uspesnom dodavanju korisnika
					Toast friendAdded = Toast.makeText(getApplicationContext(), "Prijatelj uspesno dodat!", Toast.LENGTH_SHORT);
					friendAdded.show();
					
				}
				else{
					// u suprotnom ukoliko korisnik nije nadjen
					// ispisacemo poruku da je pogresno korisnicko ime uneto
					Toast friendNotAdded = Toast.makeText(getApplicationContext(), "Pogresno uneto korisnicko ime.", Toast.LENGTH_SHORT);
					friendNotAdded.show();
				}
				// isprazni polje za findUsername
				trazenoKorisnickoIme.setText("");			
			}
		});
	
	}

}
