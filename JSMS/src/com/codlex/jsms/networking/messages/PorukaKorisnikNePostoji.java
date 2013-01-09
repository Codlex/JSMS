package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaKorisnikNePostoji implements Poruka {
	
	private static final long serialVersionUID = 1L;
	private MSGCode code;
	
	public PorukaKorisnikNePostoji() {
		this.code = MSGCode.USER_DOESNT_EXIST;
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
