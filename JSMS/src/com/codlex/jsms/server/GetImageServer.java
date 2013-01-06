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

import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.AuthMessageFailed;
import com.codlex.jsms.networking.messages.GenericSuccessMessage;
import com.codlex.jsms.networking.messages.ImageMessage;
import com.codlex.jsms.networking.messages.UserDoesntExistMessage;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;
import com.codlex.jsms.networking.messages.objects.IdentifiedRequest;

public class GetImageServer implements Server{
private static final int port = 6768;
	@Override
	public void run() {
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
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				IdentifiedRequest request = (IdentifiedRequest) message.getMsgObject();
				// user is logged in
				if( getUserService().getUserByToken(request.getTokenSignature()) != null ) {
					User user = getUserService().getUserByName(request.getRequestedUsername());
					if ( user == null ) {
						output.writeObject( new UserDoesntExistMessage() );
					}

					output.writeObject(new GenericSuccessMessage());
					Image image = getImageService().getImage(user.getToken());
					RenderedImage rImage = (BufferedImage) image;
					ImageIO.write(rImage, "PNG", socket.getOutputStream());
					socket.close();
					
				}
				else {
					output.writeObject( new AuthMessageFailed());
				}
				System.out.println("Response sent");			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
