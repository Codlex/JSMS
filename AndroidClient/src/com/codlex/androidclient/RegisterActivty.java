package com.codlex.androidclient;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.getNICService;

import java.util.concurrent.ExecutionException;

import com.codlex.androidclient.networking.CreateAccountTask;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;
import com.example.androidclient.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivty extends Activity{

	
	EditText username,password,email;
	Button registerb,backb;
	TextView log;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		username = (EditText) findViewById(R.id.username2);
		password = (EditText) findViewById(R.id.password2);
		email = (EditText) findViewById(R.id.email);
		backb = (Button) findViewById(R.id.backb);
		registerb = (Button) findViewById(R.id.registerb2);
		log = (TextView) findViewById(R.id.log);
		
		backb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				// TODO Auto-generated method stub	
			}
		});
		
		registerb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String usernameS,passwordS,emailS;
				usernameS = username.getText().toString();
				passwordS = password.getText().toString();
				emailS = email.getText().toString();
				
				CreateAccountTask createAccountTask = new CreateAccountTask();
				Message response = null;
				
				while(true){
					
					createAccountTask.execute(usernameS,passwordS,emailS);
					
					try {
						response = createAccountTask.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(response != null)
						break;
				}
				
				if(response.getMsgCode().equals(MSGCode.SUCCESS)){
					String token = (String) response.getMsgObject();
					Intent newActivity = new Intent("android.intent.action.USER");
					newActivity.putExtra("token", token);
					startActivity(newActivity);
				}
				else{
					log.setText("Ooops , something went wrong.");
				}
				
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
}
