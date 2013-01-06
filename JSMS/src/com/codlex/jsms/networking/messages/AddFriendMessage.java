package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.User;
import com.codlex.jsms.networking.messages.objects.MeAndFriend;

public class AddFriendMessage implements Message{
    private static final long serialVersionUID = 1L;

	MeAndFriend meAndFriend;
	MSGCode code;
	public AddFriendMessage(String token, String friend) {
		this.meAndFriend = new MeAndFriend(token, friend);
		this.code = MSGCode.ADD_FRIEND;
	}
	@Override
	public MSGCode getMsgCode() {
		return code;
	}
	@Override
	public Object getMsgObject() {
		return meAndFriend;
	}

}
