package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.Korisnik;

public class RegisterMessage implements Poruka{
    private static final long serialVersionUID = 1L;

	Korisnik user;
	MSGCode code;
	public RegisterMessage(Korisnik newUser) {
		this.user = newUser;
		this.code = MSGCode.REGISTER;
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
