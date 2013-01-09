package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class AuthMessageUserExists implements Poruka {
	MSGCode code;
	public AuthMessageUserExists() {
		this.code = MSGCode.USERNAME_EXISTS;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	
	@Override
	public Object getObjekatPoruke() {
		return null;
	}
}
