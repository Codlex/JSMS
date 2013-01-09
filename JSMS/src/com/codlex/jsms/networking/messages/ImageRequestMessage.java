package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.IdentifiedRequest;

public class ImageRequestMessage implements Poruka {
	IdentifiedRequest request;
	MSGCode code;
	
	public ImageRequestMessage(IdentifiedRequest request) {
		this.code = MSGCode.IMAGE_REQUEST;
		this.request = request;
	}
	
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}

	@Override
	public Object getMsgObject() {
		// TODO Auto-generated method stub
		return request;
	}

}
