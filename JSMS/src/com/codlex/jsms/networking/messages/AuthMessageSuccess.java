package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class AuthMessageSuccess implements Message {
	private MSGCode code;
	private String token;
	public AuthMessageSuccess(String token) {
		this.code = MSGCode.SUCCESS;
		this.token = token;
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}
	
	@Override
	public Object getMsgObject() {
		return token;
	}

}
