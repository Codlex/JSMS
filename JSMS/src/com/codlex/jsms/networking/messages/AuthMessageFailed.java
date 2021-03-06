package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class AuthMessageFailed implements Message {
	MSGCode code;
	public AuthMessageFailed() {
		this.code = MSGCode.AUTHERIFICATION_ERROR;
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
