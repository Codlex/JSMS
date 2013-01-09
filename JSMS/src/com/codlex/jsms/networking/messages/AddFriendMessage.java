package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.MeAndFriend;

public class AddFriendMessage implements Poruka{
    private static final long serialVersionUID = 1L;

	MeAndFriend meAndFriend;
	MSGCode code;
	public AddFriendMessage(String token, String friend) {
		this.meAndFriend = new MeAndFriend(token, friend);
		this.code = MSGCode.ADD_FRIEND;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	@Override
	public Object getObjekatPoruke() {
		return meAndFriend;
	}
	
}
