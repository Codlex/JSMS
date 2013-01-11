package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;

public class PorukaZaAutorizaciju implements Poruka{
    private static final long serialVersionUID = 1L;
    private Korisnik korisnik;
    private MSGCode code;
	
	public PorukaZaAutorizaciju(Korisnik postojeciKorisnik) {
		this.korisnik = postojeciKorisnik;
		this.code = MSGCode.AUTH;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	@Override
	public Object getObjekatPoruke() {
		return korisnik;
	}

}
