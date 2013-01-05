package com.codlex.androidclient;


import java.util.ArrayList;
import com.example.androidclient.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity{

	ListView listview;
	ArrayList<View> listOfFriends;
	String token;
	TextView tview;
	ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_layout);
		
		// dummy za listu
		String[] friends;
		friends = new String[]{" Hello ", "Hello2"};
		
		// prosledjen token
		Bundle extras = getIntent().getExtras();
		token = extras.getString("token");
		
		// testiranje prosledjenog tokena
		tview = (TextView) findViewById(R.id.testing);
		tview.setText(token);
		
		image = (ImageView) findViewById(R.id.image);
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
	
}