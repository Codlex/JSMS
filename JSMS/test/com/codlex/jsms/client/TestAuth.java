package com.codlex.jsms.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.StringMessage;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.users.BaseUser;

public class TestAuth {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1337);
			System.out.println("Connected!");
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			User user = new BaseUser("deximat", "metallica");
			Message m = new AuthMessage(user);
			output.writeObject(m);
			
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Message response = (Message) input.readObject();
			
			System.out.println(response.getMsgCode());
			System.out.println("Token:" + response.getMsgObject());
			
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
