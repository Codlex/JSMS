package com.codlex.jsms.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.StringMessage;

public class TestClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1337);
			System.out.println("Connected!");
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			StringMessage m = new StringMessage("Da Vidimo ladi ri?");
			output.writeObject((Message) m);
			if(socket.isClosed()) System.out.println("Sranje");
			System.out.println("Object sent!");
			
			StringMessage objekat3 = new StringMessage("Da Vidimo ladi ri?");
			output.writeObject(objekat3);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}

}
