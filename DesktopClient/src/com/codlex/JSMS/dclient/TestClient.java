package com.codlex.JSMS.dclient;

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
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			StringMessage m2 = (StringMessage) input.readObject();
			System.out.println("Object has been red!");
			System.out.println("Vratila mi se poruka!" + m2.getMsgObject());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
