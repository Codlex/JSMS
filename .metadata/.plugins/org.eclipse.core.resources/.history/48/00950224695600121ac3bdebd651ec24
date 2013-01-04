package com.codlex.profesor.profesorskooko;

/**
 * Ova klasa sluzi za pracenje stanja konekcije, ukoliko se student ne javi jedno vreme 
 * njegova konekcija se smatra ugasenom.
 * 
 * @author Dejan Pekter RN 13/11
 *
 */

public class timerZaTrajanjeKonekcije implements Runnable {
	//student za koga ovaj tajmer radi
	Student student;
	
	public timerZaTrajanjeKonekcije(Student student) {
		this.student = student;
	}
	
	@Override
	public void run() {
		
		while(true){
			//na svakih 200ms smanjujem preostalo vreme zivota konekcije
			student.smanjiVremeKonekcije();
			//ukoliko je vreme manje od 0 onda je konekcija prekinuta
			if( student.getPreostaloVremeKonekcije() < 0 ){
				student.unisti();
				break;
			}
			//cekam 200ms
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
