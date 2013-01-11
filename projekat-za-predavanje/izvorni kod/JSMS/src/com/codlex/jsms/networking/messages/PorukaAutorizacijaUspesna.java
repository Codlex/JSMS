package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaAutorizacijaUspesna implements Poruka {

	private static final long serialVersionUID = 1L;
	private MSGCode code;
	private String token;
	
	public PorukaAutorizacijaUspesna(String token) {
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
