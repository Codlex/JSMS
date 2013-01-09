package com.codlex.jsms.networking;

import java.io.Serializable;

public interface Poruka extends Serializable {
	public MSGCode getKodPoruke();
	public Object getMsgObject();
}
