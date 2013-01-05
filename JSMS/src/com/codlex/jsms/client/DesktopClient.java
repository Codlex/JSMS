package com.codlex.jsms.client;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import com.codlex.jsms.client.gui.LoginScreen;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;


public class DesktopClient {
	
	public static void main(String[] args) {
		//LoginScreen screen = new LoginScreen();
		//screen.setVisible(true);
		/*
		 * while (!login(
		 */
		User user = new BaseUser("deximat2", "metallica", "deximat@gmail.com");
		Message response = getNICService().createAccount(user);
		if(response.getMsgCode().equals(MSGCode.SUCCESS)) {
		   System.out.println("Account created" + user.getUsername()); 
		}
		
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("Try to login");
			System.out.println("Enter username:");
			String username = input.next();
			System.out.println("Enter password:");
			String password = input.next();
			User userToLogin = new BaseUser(username, password);
			Message responseToLogin = getNICService().logIn(userToLogin);
			System.out.println(responseToLogin.getMsgCode());
			System.out.println(responseToLogin.getMsgObject());
		}
	}
 /* while */
}
