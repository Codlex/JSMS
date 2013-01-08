package com.codlex.jsms.client;

import java.util.Random;

import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.NICS.CentralizedServerNIC;
import com.codlex.jsms.networking.users.BaseUser;

public class PingerTest {
	public static void main(String[] args) {
		CentralizedServerNIC.getNICService().logIn(new BaseUser("deximat", "metallica"));
		CentralizedServerNIC.getNICService().addFriend("deximat");
		/*Thread d = new Thread(new ImagePinger());
		d.start();*/
	}

}
