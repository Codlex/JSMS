package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;

public class AuthMessage implements Poruka{
    private static final long serialVersionUID = 1L;

	User user;
	MSGCode code;
	public AuthMessage(User existingUser) {
		this.user = existingUser;
		this.code = MSGCode.AUTH;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	@Override
	public Object getMsgObject() {
		return user;
	}

}
