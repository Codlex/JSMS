package com.codlex.jsms.client;

import java.io.ObjectInputStream.GetField;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import com.codlex.jsms.client.gui.LoginScreen;
import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;


public class DesktopClient {

	public static void main(String[] args) {
		System.out.println("Initializing client");
		
		System.out.println("*****************************");
		System.out.println("Showing login screen");
		LoginScreen login = new LoginScreen();
		login.setVisible(true);
		while(!getNICService().isLoggedIn()) {
			//smaram se
		}
		//MainScreen mainScreen = new MainScreen();
		
	}
 /* while */
		
}
