package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.messages.objects.IdentifiedRequest;

public class ImageRequestMessage implements Message {
	IdentifiedRequest request;
	MSGCode code;
	
	public ImageRequestMessage(IdentifiedRequest request) {
		this.code = MSGCode.IMAGE_REQUEST;
		this.request = request;
	}
	
	@Override
	public MSGCode getMsgCode() {
		return code;
	}

	@Override
	public Object getMsgObject() {
		// TODO Auto-generated method stub
		return request;
	}

}
