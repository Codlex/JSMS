package com.codlex.jsms.networking.NICS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.NIC;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AddFriendMessage;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.messages.GenericSuccessMessage;
import com.codlex.jsms.networking.messages.GetFriendsMessage;
import com.codlex.jsms.networking.messages.ImageMessage;
import com.codlex.jsms.networking.messages.ImageRequestMessage;
import com.codlex.jsms.networking.messages.RegisterMessage;
import com.codlex.jsms.networking.messages.objects.IdentifiedRequest;

public class CentralizedServerNIC implements NIC {

	private static final String authServer = "jsms.codlex.com";
	private static final int authPort = 1337;
	
	private static final String friendsServer = "jsms.codlex.com";
	private static final int friendsPort = 1331;
	
	private static final String getImageServer = "jsms.codlex.com";
	private static final int getImagePort = 6768;
	
	private static final String pingImageServer = "jsms.codlex.com";

    private static final int pingImagePort = 6767;
	
	
	// Logged user context
	User user;
	
	private static NIC instance;
	
	public static synchronized NIC getNICService() {
		if( instance == null ){
			instance = new CentralizedServerNIC();
		}
		return instance;
	}
	
	private CentralizedServerNIC() {
		
	}
	
	@SuppressWarnings("resource")
	@Override
	public Poruka createAccount(User user) {
		try {
		    Socket socket = new Socket(authServer, authPort);
		    System.out.println("Connected to auth server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Poruka m = new RegisterMessage(user);
		    output.writeObject(m);
		    System.out.println("User " + user.getUsername() + " sent " + 
		    		           "request to be authorised by password " + 
		    		           user.getPassword());
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getKodPoruke());
			System.out.println("Token:" + response.getMsgObject());
			
			//Setting user context
			user.setToken((String) response.getMsgObject());
			this.user = user;
			
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
	
	@SuppressWarnings("resource")
	@Override
	public Poruka logIn(User user) {
		try {
		    Socket socket = new Socket(authServer, authPort);
		    System.out.println("Connected to auth server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Poruka m = new AuthMessage(user);
		    output.writeObject(m);
		    System.out.println("User " + user.getUsername() + " sent " + 
		    		           "request to be authorised by password " + 
		    		           user.getPassword());
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getKodPoruke());
			System.out.println("Token:" + response.getMsgObject());
			
			//Setting user context
			user.setToken((String) response.getMsgObject());
			this.user = user;
			
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
	public Poruka sendMessage(Poruka message)  {
		System.out.println("Not implemented");
		return null;
	}
	
	@SuppressWarnings("resource")
	@Override
	public Poruka addFriend(String username) {
		try {
		    Socket socket = new Socket(friendsServer, friendsPort);
		    System.out.println("Connected to friends server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Poruka m = new AddFriendMessage(this.user.getToken(), username);
		    output.writeObject(m);
		    System.out.println("Add friend request sent");
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getKodPoruke());
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
	
	@SuppressWarnings("resource")
	@Override
	public Poruka getFriends() {
		try {
		    Socket socket = new Socket(friendsServer, friendsPort);
		    System.out.println("Connected to friends server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Poruka m = new GetFriendsMessage(this.user.getToken());
		    output.writeObject(m);
		    System.out.println("Get friends request sent");
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getKodPoruke());
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
	
	
	@SuppressWarnings("resource")
	@Override
	public Poruka getScreen(String username) {
		try {
		    Socket socket = new Socket(getImageServer, getImagePort);
		    System.out.println("Connected to getImage server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    IdentifiedRequest request = new IdentifiedRequest(this.user.getToken(), username);
		    Poruka m = new ImageRequestMessage(request);
		    output.writeObject(m);
		    System.out.println("Get image request sent");
		    
		    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Poruka response = (Poruka) input.readObject();
			System.out.println("Server responded with code");
			System.out.println(response.getKodPoruke());
			System.out.println("Token:" + response.getMsgObject());
			if(response.getKodPoruke().equals(MSGCode.SUCCESS)) {
				GenericSuccessMessage message = (GenericSuccessMessage)response;
				message.setMsgObject(socket.getInputStream());
			}		      
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
	
	@SuppressWarnings("resource")
	@Override
	public Poruka pingScreen() {
		try {
		    Socket socket = new Socket(pingImageServer, pingImagePort);
		    System.out.println("Connected to pingImage server!");
		    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    Poruka m = new ImageMessage(this.user.getToken());
		    output.writeObject(m);
		    System.out.println("Ping image request sent");
		    GenericSuccessMessage response = new GenericSuccessMessage();
			response.setMsgObject(socket.getOutputStream());  
			return response;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	@Override
	public User getLoggedUser() {
		return user;
	}
	
	@Override
	public boolean isLoggedIn() {
		return user != null;
	}
	
	@Override
	public void logOut() {
		user = null;
	}
	
}
