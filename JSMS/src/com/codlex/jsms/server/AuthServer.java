package com.codlex.jsms.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessage;
import com.codlex.jsms.networking.messages.AuthMessageFailed;
import com.codlex.jsms.networking.messages.AuthMessageSuccess;
import com.codlex.jsms.networking.messages.AuthMessageUserExists;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.server.users.UserService.*;
import static com.codlex.jsms.networking.MSGCode.*;

/**
 * AuthServer je napravljen da prima konekcije od neprijavljenih 
 * korisnika na sistem i da im vraca poruku u obliku uspeha ili 
 * neuspeha, sa tokenom odnosno bez njega respektivno. 
 * 
 * @author Dejan Pekter <deximat@gmail.com>
 *
 */
public class AuthServer implements Server{
	private static final int port = 1337;
	
	@Override
	public void run() {
		
		//tesing purposes
		User user = new BaseUser("deximat", "metallica");
		if(getUserService().createUser(user) != null) {
			System.out.println("Added artificial user");
		}
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server started on port " + port);
			while(true) {
				Socket socket = server.accept();
				System.out.println("Connection accepted");
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				System.out.println("Reading message");
				Message message = (Message) input.readObject();
				System.out.println("Message recived");
				Message response = processMessage(message);
				System.out.println("Message processed, sending response");
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(response);				
				System.out.println("Response sent");
			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Message processMessage(Message m) {
		User user = null;
		String token = null;
		switch (m.getMsgCode()) {
		case AUTH: 
			System.out.println("Detected AUTH message");
			user = (User) m.getMsgObject();
			token = getUserService().login(user);
			if(token == null) {
				return new AuthMessageFailed();				
			}
			return new AuthMessageSuccess(token);
		case REGISTER:
			System.out.println("Detected REGISTER message");
			user = (User) m.getMsgObject();
			token = getUserService().createUser(user);
			if(token == null) {
				return new AuthMessageUserExists();				
			}
			return new AuthMessageSuccess(token);
			
		default:
			return null;
		}
		
	}
	
	
}
