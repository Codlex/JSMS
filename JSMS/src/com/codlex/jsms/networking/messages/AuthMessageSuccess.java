package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class AuthMessageSuccess implements Poruka {
	private MSGCode code;
	private String token;
	public AuthMessageSuccess(String token) {
		this.code = MSGCode.SUCCESS;
		this.token = token;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	
	@Override
	public Object getObjekatPoruke() {
		return token;
	}

}
