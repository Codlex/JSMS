package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.UserService.getUserService;
import static com.codlex.jsms.server.users.ImageService.getImageService;

import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.GenericSuccessMessage;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;
import com.codlex.jsms.networking.users.BaseUser;

/**
 * Server koji prihvata slike ekrana korisnika i osvezava svoju bazu. 
 */
public class PingImageServer implements Server{
	
private static final int port = 6767;
	
	public void run() {
		
			ServerSocket server = null;
			try {
				server = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Socket socket = null;
			System.out.println("[PING_IMAGE::SERVER] Server started on port " + port);
			while(true) {
				System.out.println("[PING_IMAGE::SERVER] While started!");
				try {
				socket = server.accept();
				socket.setSoTimeout(500);
				System.out.println("[PING_IMAGE::SERVER] Connection accepted");
				if(socket.isClosed()) {
					continue;
				}
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());;
				System.out.println("[PING_IMAGE::SERVER] Reading message");
				Message message = (Message) input.readObject();
				System.out.println("[PING_IMAGE::SERVER] Message recived - identified");
				System.out.println("[PING_IMAGE::SERVER] Reading image...");
				Image image = ImageIO.read(socket.getInputStream());
				System.out.println("[PING_IMAGE::SERVER] Finished reading...");
				String token = (String) message.getMsgObject();
				getImageService().setImage(token, image);				
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[PING_IMAGE::SERVER] Exception caucthsdjc.");

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				System.out.println("[PING_IMAGE::SERVER] While continued");
			}
			
	}
	

}
