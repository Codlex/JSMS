package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;

public class UserDoesntExistMessage implements Poruka {
	MSGCode code;
	public UserDoesntExistMessage() {
		this.code = MSGCode.USER_DOESNT_EXIST;
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
