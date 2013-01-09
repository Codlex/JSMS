package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.JaIPrijatelj;

public class PorukaZaDodavanjePrijatelja implements Poruka{
    
	private static final long serialVersionUID = 1L;
    private JaIPrijatelj jaIPrijatelj;
    private MSGCode code;
    
	public PorukaZaDodavanjePrijatelja(String token, String prijatelj) {
		this.jaIPrijatelj = new JaIPrijatelj(token, prijatelj);
		this.code = MSGCode.ADD_FRIEND;
	}
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}
	@Override
	public Object getObjekatPoruke() {
		return jaIPrijatelj;
	}
	
}
