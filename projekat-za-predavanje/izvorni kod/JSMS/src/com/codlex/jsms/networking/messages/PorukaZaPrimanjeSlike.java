package com.codlex.jsms.networking.messages;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Poruka;
import com.codlex.jsms.networking.messages.objects.ZahtevZaIdentifikacijom;

public class PorukaZaPrimanjeSlike implements Poruka {

	private static final long serialVersionUID = 1L;
	private ZahtevZaIdentifikacijom zahtev;
	private MSGCode code;
	
	public PorukaZaPrimanjeSlike(ZahtevZaIdentifikacijom zahtev) {
		this.code = MSGCode.IMAGE_REQUEST;
		this.zahtev = zahtev;
	}
	
	@Override
	public MSGCode getKodPoruke() {
		return code;
	}

	@Override
	public Object getObjekatPoruke() {
		return zahtev;
	}

}
