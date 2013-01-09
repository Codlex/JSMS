package com.codlex.jsms.networking.messages;

import java.awt.Image;
import java.util.Collection;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.IdentifiedImage;

public class FriendsMessage implements Poruka {
	Collection<String> friends;
	MSGCode code;
	
	public FriendsMessage( Collection<String> friends) {
		this.code = MSGCode.SUCCESS;
		this.friends = friends;		
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}

	@Override
	public Object getObjekatPoruke() {
		return friends;
	}

}
