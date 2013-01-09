package com.codlex.jsms.networking.messages;

import java.awt.Image;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;

public class ImageMessage implements Poruka {
	String token;
	MSGCode code;
	
	public ImageMessage(String token) {
		this.code = MSGCode.IMAGE_PING;
		this.token = token;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}

	@Override
	public Object getMsgObject() {
		return token;
	}

}
