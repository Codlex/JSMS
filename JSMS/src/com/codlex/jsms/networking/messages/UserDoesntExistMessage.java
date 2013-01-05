package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class UserDoesntExistMessage implements Message {
	MSGCode code;
	public UserDoesntExistMessage() {
		this.code = MSGCode.USER_DOESNT_EXIST;
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}
	
	@Override
	public Object getMsgObject() {
		return null;
	}
}
