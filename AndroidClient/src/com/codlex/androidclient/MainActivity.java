package com.codlex.androidclient;


import java.util.concurrent.ExecutionException;
import com.codlex.androidclient.networking.LoginTask;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.example.androidclient.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button loginb,registerb;
	EditText username,password;
	TextView wrong_username_password;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        loginb = (Button) findViewById(R.id.loginb);
        registerb = (Button) findViewById(R.id.registerb);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        wrong_username_password = (TextView) findViewById(R.id.wa);
        
        loginb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// provera da li user postoji
				// vraca true ili false
				// ako postoji
				//User user = new BaseUser(username.toString(),password.toString());
				
				System.out.println(username.toString());
				
				String usernameS,passwordS;
				
				usernameS = username.getText().toString();
				passwordS = password.getText().toString();
				
				// napravimo task i response
				LoginTask loginTask = new LoginTask();
				Message response = null;
				
				while(true){
					loginTask.execute(usernameS,passwordS);
				
				// pokupimo odgovor
					try {
						response = loginTask.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 if(response != null) break;
				}
			
				if(response.getMsgCode().equals(MSGCode.SUCCESS)){
					String token = (String) response.getMsgObject();
					Intent newActivity = new Intent("android.intent.action.USER");
					newActivity.putExtra("token", token);
					startActivity(newActivity);
				}
				else{
					wrong_username_password.setText("Wrong username or password.");
				}
				
				// TODO Auto-generated method stub
			}
		});
        
        registerb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent("android.intent.action.REGISTER"));
				// TODO Auto-generated method stub
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
