package com.codlex.jsms.networking.messages;

import java.util.Collection;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class PorukaSaPrijateljima implements Poruka {

	private static final long serialVersionUID = 1L;
	private Collection<String> prijatelji;
	private MSGCode code;
	
	public PorukaSaPrijateljima( Collection<String> prijatelji) {
		this.code = MSGCode.SUCCESS;
		this.prijatelji = prijatelji;		
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}

	@Override
	public Object getObjekatPoruke() {
		return prijatelji;
	}

}
