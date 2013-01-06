package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.objects.MeAndFriend;

public class GetFriendsMessage implements Message{
    private static final long serialVersionUID = 1L;

	String token;
	MSGCode code;
	public GetFriendsMessage(String token) {
		this.code = MSGCode.GET_FRIENDS;
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
