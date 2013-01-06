package com.codlex.jsms.server;

public class MainServer {
	public static void main(String[] args) {
		System.out.println("Codlex oko server has started!");
		
		System.out.println("***************************************");
		System.out.println("Initializing auth thread");
		Server auth = new AuthServer();
		Thread authThread = new Thread(auth);
		authThread.start();
		System.out.println("Auth server thread started");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Initializing pingImageServer");
		Server pingImage = new PingImageServer();
		Thread pingImageThread = new Thread(pingImage);
		pingImageThread.start();
		System.out.println("PingImageServer thread has been started");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Initializing getImageServer");
		Server getImage = new GetImageServer();
		Thread getImageServerThread = new Thread(getImage);
		getImageServerThread.start();
		System.out.println("GetImageServer thread has been started");
		System.out.println("***************************************");
		
		System.out.println("***************************************");
		System.out.println("Initializing friendsServer");
		Server friendsServer = new FriendsServer();
		Thread friendsServerThread = new Thread(friendsServer);
		friendsServerThread.start();
		System.out.println("FriendsServer thread has been started");
		System.out.println("***************************************");

		
		System.out.println("Server system fully initialized!");
		
	}

}
