package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;

public class AuthMessage implements Message{
	User user;
	MSGCode code;
	public AuthMessage(User existingUser) {
		this.user = existingUser;
		this.code = MSGCode.AUTH;
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}
	@Override
	public Object getMsgObject() {
		return user;
	}

}
