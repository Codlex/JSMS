package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;

public class AuthMessage implements Poruka{
    private static final long serialVersionUID = 1L;

	Korisnik user;
	MSGCode code;
	public AuthMessage(Korisnik existingUser) {
		this.user = existingUser;
		this.code = MSGCode.AUTH;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	@Override
	public Object getObjekatPoruke() {
		return user;
	}

}
