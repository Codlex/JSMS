package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;

public class GenericSuccessMessage implements Message {
	private MSGCode code;
	private Object o;
	public GenericSuccessMessage() {
		this.code = MSGCode.SUCCESS;
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}
	
	@Override
	public Object getMsgObject() {
		return o;
	}
	
	public void setMsgObject(Object o) {
		this.o = o;
	}


}
