package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaUspeha implements Poruka {

	private static final long serialVersionUID = 1L;
	private MSGCode code;
	private Object objekat;
	
	public PorukaUspeha() {
		this.code = MSGCode.SUCCESS;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	
	@Override
	public Object getObjekatPoruke() {
		return objekat;
	}
	
	public void setObjekatPoruke(Object objekat) {
		this.objekat = objekat;
	}


}
