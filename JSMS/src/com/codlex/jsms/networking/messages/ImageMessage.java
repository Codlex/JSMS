package com.codlex.jsms.networking.messages;

import java.awt.Image;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;

public class ImageMessage implements Message {
	IdentifiedImage img;
	MSGCode code;
	
	public ImageMessage(String token, Image image) {
		this.code = MSGCode.IMAGE_PING;
		this.img = new IdentifiedImage(token, image);		
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}

	@Override
	public Object getMsgObject() {
		return img;
	}

}
