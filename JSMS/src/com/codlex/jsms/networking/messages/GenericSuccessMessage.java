package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class GenericSuccessMessage implements Poruka {
	private MSGCode code;
	private Object o;
	public GenericSuccessMessage() {
		this.code = MSGCode.SUCCESS;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	
	@Override
	public Object getObjekatPoruke() {
		return o;
	}
	
	public void setMsgObject(Object o) {
		this.o = o;
	}


}
