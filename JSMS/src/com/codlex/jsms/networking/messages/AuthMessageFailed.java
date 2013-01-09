package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class AuthMessageFailed implements Poruka {
	MSGCode code;
	public AuthMessageFailed() {
		this.code = MSGCode.AUTHERIFICATION_ERROR;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	
	@Override
	public Object getMsgObject() {
		return null;
	}
	

}
