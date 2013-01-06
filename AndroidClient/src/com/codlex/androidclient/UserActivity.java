package com.codlex.androidclient;


import com.example.androidclient.R;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity{

	ListView listview;
	ArrayList<View> listOfFriends;
	String token,username;
	TextView helloview;
	Button logoutb,addfriendb;
	AlertDialog alertDialog;
	Context context;
	
	// dialog
	TextView wrong_username;
	Button backb2,addb;
	EditText findUsername;
	View dialoglayout;
	
	// builder za dialogBox
	AlertDialog.Builder builder;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_layout);
		
		
		
		// inicijalizuj dijalog ceo
		builder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		
		
		// dummy za listu
		String[] friends;
		friends = new String[]{" Hello ", "Hello2"};
		
		// prosledjen token
		Bundle extras = getIntent().getExtras();
		token = extras.getString("token");
		// prosledjen username
		username = extras.getString("username");
		
		// podesimo hello :)
		helloview = (TextView) findViewById(R.id.hello);
		helloview.setText("Hello " + username + "!");
		
		// logout dugme
		logoutb = (Button) findViewById(R.id.logoutb);
		logoutb.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				finish();
				// TODO Auto-generated method stub
			}
		});
		
		// addfriend dugme
		addfriendb = (Button) findViewById(R.id.addfriendb);
		addfriendb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// dialog za dodavanje prijatelja
				
				dialoglayout = inflater.inflate(R.layout.add_friend_dialog_layout, (ViewGroup) getCurrentFocus(),false);

				
				
				builder.setView(dialoglayout);
				builder.setTitle("Add a friend, why not?!");
				alertDialog = builder.create();
				alertDialog.show();
				
				initilaizeDialog();
			
			}
		});
		
		// lista prijatelja
		listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(new ArrayAdapter<String>(this, R.layout.item,friends));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	void initilaizeDialog(){
		// dugmici

		backb2	= (Button) alertDialog.findViewById(R.id.backb2);
		addb	= (Button) alertDialog.findViewById(R.id.add);
		findUsername = (EditText) alertDialog.findViewById(R.id.findUsername);
		wrong_username = (TextView) alertDialog.findViewById(R.id.wa2);
	
		// listeneri
		backb2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				alertDialog.cancel();
				// TODO Auto-generated method setup
			}
		});
		
		addb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// unet string
			//	String targetUsername = findUsername.getText().toString();
				// trazi nesto negde HO
				
			}
		});
	
	}
}
