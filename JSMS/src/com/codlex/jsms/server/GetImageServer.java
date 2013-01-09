package com.codlex.jsms.server;

import static com.codlex.jsms.server.users.ImageService.getImageService;
import static com.codlex.jsms.server.users.UserService.getUserService;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessageFailed;
import com.codlex.jsms.networking.messages.GenericSuccessMessage;
import com.codlex.jsms.networking.messages.ImageMessage;
import com.codlex.jsms.networking.messages.UserDoesntExistMessage;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;
import com.codlex.jsms.networking.messages.objects.IdentifiedRequest;
import com.codlex.jsms.utils.CompressionImageWriter;

public class GetImageServer implements Server {
	private static final int port = 6768;

	@Override
	public void run() {
		
			ServerSocket server = null;
			try {
				server = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("[GET_IMAGE::SERVER] Server started on port " + port);
			while(true) {
				try {
					Socket socket = server.accept();
					socket.setSoTimeout(500);
					System.out.println("[GET_IMAGE::SERVER] Connection accepted");
					if(socket.isClosed()) {
						continue;
					}
					ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
					System.out.println("[GET_IMAGE::SERVER] Reading message");
					Poruka message = (Poruka) input.readObject();
					System.out.println("[GET_IMAGE::SERVER] Message recived");
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					IdentifiedRequest request = (IdentifiedRequest) message.getMsgObject();
					// user is logged in
					if( getUserService().getUserByToken(request.getTokenSignature()) != null ) {
						User user = getUserService().getUserByName(request.getRequestedUsername());
						if ( user == null ) {
							System.out.println("[GET_IMAGE::SERVER] User " + request.getRequestedUsername() + " is not found!");
							output.writeObject( new UserDoesntExistMessage() );
						}
						output.writeObject(new GenericSuccessMessage());
						System.out.println("[GET_IMAGE::SERVER] Success message written!");
						System.out.println("[GET_IMAGE::SERVER] Sending image!");
						Image image = getImageService().getImage(user.getToken());
						RenderedImage rImage = (BufferedImage) image;
		        		CompressionImageWriter.jpgLowWrite((BufferedImage) image, socket.getOutputStream());
		        		System.out.println("[GET_IMAGE::SERVER] Image sent!");
						socket.close();
						
					}
					else {
						output.writeObject( new AuthMessageFailed());
						System.out.println("[GET_IMAGE::SERVER] Fail sent");			

					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[GET_IMAGE::SERVER] Exception cacushdhsdfkj");
		
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				System.out.println("[GET_IMAGE::SERVER] While continued!");
				
	    }
	}
}
