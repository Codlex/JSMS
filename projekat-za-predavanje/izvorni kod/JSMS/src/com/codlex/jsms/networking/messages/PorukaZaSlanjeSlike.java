package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaZaSlanjeSlike implements Poruka {

	private static final long serialVersionUID = 1L;
	private	String token;
	private MSGCode code;
	
	public PorukaZaSlanjeSlike(String token) {
		this.code = MSGCode.IMAGE_PING;
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
