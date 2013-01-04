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
public class AuthServer {
	private static final int port = 1337;
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(port);
			
			while(true) {
				Socket socket = server.accept();
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) input.readObject();
				Message response = processMessage(message);
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(response);				
			}
		} catch (IOException e) {
			System.out.println("Nije moguce otvoriti server na zadatom portu.");
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
			user = (User) m.getMsgObject();
			token = getUserService().login(user);
			if(token == null) {
				return new AuthMessageFailed();				
			}
			return new AuthMessageSuccess(token);
		case REGISTER:
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
