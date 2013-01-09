package com.codlex.jsms.client;

import java.util.Random;

import com.codlex.jsms.networking.Korisnik;
import com.codlex.jsms.networking.NICS.CentralizovaniNIC;
import com.codlex.jsms.networking.users.OsnovniKorisnik;

public class PingerTest {
	public static void main(String[] args) {
		CentralizovaniNIC.getNICService().prijaviSe(new OsnovniKorisnik("deximat", "metallica"));
		CentralizovaniNIC.getNICService().dodajPrijatelja("deximat");
		/*Thread d = new Thread(new ImagePinger());
		d.start();*/
	}

}
