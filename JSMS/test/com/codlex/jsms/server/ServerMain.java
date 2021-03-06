package com.codlex.jsms.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.StringMessage;
import com.codlex.jsms.server.*;



public class ServerMain {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1337);
			Socket socketa = null;
			while(true) {
				if(socketa != null) {
					ObjectInputStream a = new ObjectInputStream(socketa.getInputStream());
					StringMessage message = (StringMessage) a.readObject();
					System.out.println("Happy song!");
					System.out.println(message.getMsgObject());

				}
				System.out.println("System is waiting for connections");
				Socket socket = server.accept();
				ObjectInputStream a = new ObjectInputStream(socket.getInputStream());
				StringMessage message = (StringMessage) a.readObject();
				System.out.println("Message I got is:");
				System.out.println(message.getMsgObject());
				System.out.println("Echoing message back to user:");
				ObjectOutputStream b = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("Created output stream!");
				b.writeObject(message);
				System.out.println("Message sent");
				socketa = socket;
				
			}
		} catch (IOException e) {
			System.out.println("Port you are trying to open is busy!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
