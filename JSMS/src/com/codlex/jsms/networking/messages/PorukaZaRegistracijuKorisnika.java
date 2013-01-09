package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;

public class PorukaZaRegistracijuKorisnika implements Poruka{
	
    private static final long serialVersionUID = 1L;
	private Korisnik korisnik;
	private MSGCode code;
	
	public PorukaZaRegistracijuKorisnika(Korisnik noviKorisnik) {
		this.korisnik = noviKorisnik;
		this.code = MSGCode.REGISTER;
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
