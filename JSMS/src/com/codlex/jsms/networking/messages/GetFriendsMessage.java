package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.objects.MeAndFriend;

public class GetFriendsMessage implements Poruka{
    private static final long serialVersionUID = 1L;

	String token;
	MSGCode code;
	public GetFriendsMessage(String token) {
		this.code = MSGCode.GET_FRIENDS;
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
