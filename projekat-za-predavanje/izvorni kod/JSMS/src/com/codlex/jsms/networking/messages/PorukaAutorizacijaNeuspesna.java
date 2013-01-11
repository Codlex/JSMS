package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaAutorizacijaNeuspesna implements Poruka {

	private static final long serialVersionUID = 1L;
	private MSGCode code;
	
	public PorukaAutorizacijaNeuspesna() {
		this.code = MSGCode.AUTHERIFICATION_ERROR;
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
