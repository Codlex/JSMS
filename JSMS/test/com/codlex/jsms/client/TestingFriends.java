package com.codlex.jsms.client;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;

import java.util.Collection;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.users.BaseUser;

public class TestingFriends {
	public static void main(String[] args) {
		getNICService().createAccount(new BaseUser("mojDrug", "ananas", "jojo"));
		getNICService().createAccount(new BaseUser("mojDrug2", "ananas", "jojo"));

		User user = new BaseUser("deximat", "metallica");
		getNICService().logIn(user);
		
		if(getNICService().isLoggedIn()) {
			System.out.println("Uspelo logovanje");
			System.out.println("Bravo " + getNICService().getLoggedUser().getUsername());
		}
		
		MSGCode code = getNICService().addFriend("mojDrug2").getKodPoruke();
		System.out.println("Friend added? code: " + code);
		Poruka m = getNICService().getFriends();
		Collection<String> friends = (Collection<String>) m.getMsgObject();
		if (friends == null || friends.isEmpty()) {
			System.out.println("No friends");
			return;
		}
		for(String friend : friends) {
			System.out.println(friend);
		}
		
		
		
		
	}

}
