package com.codlex.jsms.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Stack;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.messages.AuthMessageFailed;
import com.codlex.jsms.networking.messages.AuthMessageSuccess;
import com.codlex.jsms.networking.messages.AuthMessageUserExists;
import com.codlex.jsms.networking.messages.FriendsMessage;
import com.codlex.jsms.networking.messages.GenericSuccessMessage;
import com.codlex.jsms.networking.messages.UserDoesntExistMessage;
import com.codlex.jsms.networking.messages.objects.MeAndFriend;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.server.users.UserService.*;
import static com.codlex.jsms.server.friends.FriendsService.*;

import static com.codlex.jsms.networking.MSGCode.*;


public class FriendsServer implements Server{
	private static final int port = 1331;
	
	@Override
	public void run() {
		
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("[FRIENDS::SERVER] Server started on port " + port);
			while(true) {
				Socket socket = server.accept();
				System.out.println("[FRIENDS::SERVER] Connection accepted");
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				System.out.println("[FRIENDS::SERVER] Reading message");
				Message message = (Message) input.readObject();
				System.out.println("[FRIENDS::SERVER] Message recived");
				Message response = processMessage(message);
				System.out.println("[FRIENDS::SERVER] Message processed, sending response");
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(response);				
				System.out.println("[FRIENDS::SERVER] Response sent");
			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Message processMessage(Message m) {
		
		switch (m.getMsgCode()) {
		case GET_FRIENDS: 
			System.out.println("[FRIENDS::SERVER] Detected GET_FRIENDS message");
			String token = (String) m.getMsgObject();
			User user = getUserService().getUserByToken(token);
			if(user == null) {
				return new UserDoesntExistMessage();
			}
			String username = getUserService().getUserByToken(token).getUsername();
			Collection<String> friends = getFriendsService().getFriends(username);
			if( friends == null || friends.isEmpty() ) {
				return new FriendsMessage(new Stack<String>());
			}
			return new FriendsMessage(friends);
			
		case ADD_FRIEND:
			System.out.println("[FRIENDS::SERVER] Detected ADD_FRIEND message");
			MeAndFriend meAndFriend = (MeAndFriend) m.getMsgObject();
			User me = getUserService().getUserByToken(meAndFriend.getToken());
			User him = getUserService().getUserByName(meAndFriend.getFriend());
			if(me == null || him == null ) {
				return new UserDoesntExistMessage();
			}
			getFriendsService().addFriend(me.getUsername(), him.getUsername());
			System.out.println("[FRIENDS::SERVER] " + me.getUsername() + " meet " + him.getUsername());
			return new GenericSuccessMessage();
		
			
		default:
			return null;
		}
		
		
	}
	
	
}
