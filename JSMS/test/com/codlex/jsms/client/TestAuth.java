package com.codlex.jsms.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

public class TestAuth {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1337);
			System.out.println("Connected!");
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			Korisnik user = new OsnovniKorisnik("deximat", "metallica");
			Poruka m = new AuthMessage(user);
			output.writeObject(m);
			
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			
			System.out.println(response.getKodPoruke());
			System.out.println("Token:" + response.getObjekatPoruke());
			
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
