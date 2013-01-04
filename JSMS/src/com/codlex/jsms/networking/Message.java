package com.codlex.jsms.networking;

import java.io.Serializable;

public interface Message extends Serializable {
	public MSGCode getMsgCode();
	public Object getMsgObject();
}
