package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class AuthMessageUserExists implements Message {
	MSGCode code;
	public AuthMessageUserExists() {
		this.code = MSGCode.USERNAME_EXISTS;
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
