package com.codlex.jsms.androidclient;


import com.codlex.jsms.androidclient.model.AndroidFriendListModel;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity{
	
	// Friend model
	AndroidFriendListModel androidFriendListModel;
	
	ListView listview;
	ArrayList<View> listOfFriends;
	String token,username;
	TextView helloview;
	Button logoutb,addfriendb;
	
	Context context;
	
	// addFriendDialog components
	TextView wrong_username;
	Button backb2,addb;
	EditText findUsername;
	View dialoglayout;
	
	// viewScreenDialog components
	Button backb3;
	ImageView userImage;
	
	// Dialogs
	AlertDialog alertDialogAddFriend;
	AlertDialog alertDialogViewScreen;
	//AlertDialog 
	// builder za addFriendDialog
	AlertDialog.Builder addFriendBuilder;
	// builder za view screen
	AlertDialog.Builder screenViewBuilder;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_layout);
		
		
		
		
		
		// inicijalizacija add friend builder-a
		addFriendBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = getLayoutInflater();
		// incijalizacija screen view builder-a
		screenViewBuilder = new AlertDialog.Builder(this);
		final LayoutInflater inflater2 = getLayoutInflater();

		// inzijalizacija modela
		androidFriendListModel = new AndroidFriendListModel(token);
		// lista frendova
		ArrayList<String> friends;
		androidFriendListModel.getFriends();
		friends = (ArrayList<String>) androidFriendListModel.getUsernamesOfFriends();
		
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

				addFriendBuilder.setView(dialoglayout);
				addFriendBuilder.setTitle("Add a friend, why not?!");
				alertDialogAddFriend = addFriendBuilder.create();
				alertDialogAddFriend.show();
				initilaizeAddFriendDialog();
			
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

	
	void initilaizeViewScreenDialog(){
		backb3 = (Button) alertDialogViewScreen.findViewById(R.id.backb3);
		userImage = (ImageView)alertDialogViewScreen.findViewById(R.id.friendimage); 
	
		backb3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialogViewScreen.cancel();
			}
		});
	}
	
	void initilaizeAddFriendDialog(){
		// dugmici

		backb2	= (Button) alertDialogAddFriend.findViewById(R.id.backb2);
		addb	= (Button) alertDialogAddFriend.findViewById(R.id.add);
		findUsername = (EditText) alertDialogAddFriend.findViewById(R.id.findUsername);
		wrong_username = (TextView) alertDialogAddFriend.findViewById(R.id.wa2);
	
		// listeneri
		backb2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				alertDialogAddFriend.cancel();
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
