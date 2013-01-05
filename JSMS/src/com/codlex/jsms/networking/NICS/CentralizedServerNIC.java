package com.codlex.jsms.networking.NICS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.NIC;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.messages.RegisterMessage;
import com.codlex.jsms.networking.users.BaseUser;

public class CentralizedServerNIC implements NIC {
	private static final String server = "localhost";
	private static final int port = 3663;
	
	private static final String authServer = "localhost";
	private static final int authPort = 1337;
	
	private static NIC instance;
	
	public static synchronized NIC getNICService() {
		if( instance == null ){
			instance = new CentralizedServerNIC();
		}
		return instance;
	}
	
	private CentralizedServerNIC() {
		
	}
	
	@Override
	public Message createAccount(User user) {
		try {
		    Socket socket = new Socket(authServer, authPort);
		    System.out.println("Connected to auth server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Message m = new RegisterMessage(user);
		    output.writeObject(m);
		    System.out.println("User " + user.getUsername() + " sent " + 
		    		           "request to be authorised by password " + 
		    		           user.getPassword());
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Message response = (Message) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getMsgCode());
			System.out.println("Token:" + response.getMsgObject());
			
			output.close();
		    input.close();
		      
			return response;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Message logIn(User user) {
		try {
		    Socket socket = new Socket(authServer, authPort);
		    System.out.println("Connected to auth server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Message m = new AuthMessage(user);
		    output.writeObject(m);
		    System.out.println("User " + user.getUsername() + " sent " + 
		    		           "request to be authorised by password " + 
		    		           user.getPassword());
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Message response = (Message) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getMsgCode());
			System.out.println("Token:" + response.getMsgObject());
			
			output.close();
		    input.close();
		      
			return response;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public Message sendMessage(Message message)  {
		System.out.println("Not implemented");
		return null;
	}
	
	
}
