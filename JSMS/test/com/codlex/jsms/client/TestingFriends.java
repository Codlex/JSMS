package com.codlex.jsms.client;

import static com.codlex.jsms.networking.NICS.CentralizovaniNIC.*;

import java.util.Collection;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

public class TestingFriends {
	public static void main(String[] args) {
		getNICService().napraviNalog(new OsnovniKorisnik("mojDrug", "ananas", "jojo"));
		getNICService().napraviNalog(new OsnovniKorisnik("mojDrug2", "ananas", "jojo"));

		Korisnik user = new OsnovniKorisnik("deximat", "metallica");
		getNICService().prijaviSe(user);
		
		if(getNICService().jeUlogovan()) {
			System.out.println("Uspelo logovanje");
			System.out.println("Bravo " + getNICService().getTrenutnoUlogovanKorisnik().getKorisnickoIme());
		}
		
		MSGCode code = getNICService().dodajPrijatelja("mojDrug2").getKodPoruke();
		System.out.println("Friend added? code: " + code);
		Poruka m = getNICService().getPrijatelji();
		Collection<String> friends = (Collection<String>) m.getObjekatPoruke();
		if (friends == null || friends.isEmpty()) {
			System.out.println("No friends");
			return;
		}
		for(String friend : friends) {
			System.out.println(friend);
		}
		
		
		
		
	}

}
