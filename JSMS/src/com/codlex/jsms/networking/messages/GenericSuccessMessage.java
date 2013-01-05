package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class GenericSuccessMessage implements Message {
	private MSGCode code;
	public GenericSuccessMessage() {
		this.code = MSGCode.SUCCESS;
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
