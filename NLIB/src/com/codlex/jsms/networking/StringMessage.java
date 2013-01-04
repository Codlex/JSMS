package com.codlex.jsms.networking;

import java.io.Serializable;


public class StringMessage implements Message, Serializable {
    private static final long serialVersionUID = 1L;

	String s;
	public StringMessage(String ss) {
		this.s = ss;
	}
	@Override
	public MSGCodes getMsgCode() {
		// TODO Auto-generated method stub
		return MSGCodes.SUCCESS;
	}

	@Override
	public String getMsgObject() {
		// TODO Auto-generated method stub
		return s;
	}

}
