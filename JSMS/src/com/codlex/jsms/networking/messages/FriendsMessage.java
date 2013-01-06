package com.codlex.jsms.networking.messages;

import java.awt.Image;
import java.util.Collection;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;

public class FriendsMessage implements Message {
	Collection<String> friends;
	MSGCode code;
	
	public FriendsMessage( Collection<String> friends) {
		this.code = MSGCode.SUCCESS;
		this.friends = friends;		
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}

	@Override
	public Object getMsgObject() {
		return friends;
	}

}
