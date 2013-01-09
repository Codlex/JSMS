package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaSaZahtevomZaPrijatelje implements Poruka{
	
    private static final long serialVersionUID = 1L;
    private String token;
    private MSGCode code;
    
	public PorukaSaZahtevomZaPrijatelje(String token) {
		this.code = MSGCode.GET_FRIENDS;
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
