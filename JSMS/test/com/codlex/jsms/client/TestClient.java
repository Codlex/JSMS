package com.codlex.jsms.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.StringMessage;

public class TestClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1337);
			System.out.println("Connected!");
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			StringMessage m = new StringMessage("Da Vidimo ladi ri?");
			output.writeObject((Poruka) m);
			if(socket.isClosed()) System.out.println("Sranje");
			System.out.println("Object sent!");
			
			
			ObjectInputStream a = new ObjectInputStream(socket.getInputStream());
			Poruka message = (Poruka) a.readObject();
			System.out.println("Message I got is:");
			System.out.println(message.getKodPoruke());
			System.out.println(message.getMsgObject());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
