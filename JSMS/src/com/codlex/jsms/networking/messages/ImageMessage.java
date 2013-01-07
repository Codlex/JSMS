package com.codlex.jsms.networking.messages;

import java.awt.Image;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;

public class ImageMessage implements Message {
	String token;
	MSGCode code;
	
	public ImageMessage(String token) {
		this.code = MSGCode.IMAGE_PING;
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
