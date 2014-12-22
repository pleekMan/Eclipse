package Clips.Hedera;

import java.util.ArrayList;

import globals.Clip;

public class Hedera extends Clip {

	ArrayList<Hoja> hojas;

	float hx = 30;
	float hy = 550;
	boolean termino;
	Timer tempo;

	public Hedera() {
		super();
	}

	@Override
	public void load() {
		super.load();

		hojas = new ArrayList<Hoja>();
		tempo = new Timer();
		tempo.setDuration(500);
		tempo.start();
		termino = false;

	}

	@Override
	public void updateProjection() {

	}

	@Override
	public void renderProjection() {
		for (int i = 0; i < hojas.size(); i++) {
			Hoja hojaActual = hojas.get(i); // va viendo cuantas hojas hay
			hojaActual.dibujar();
		}
		if (tempo.isFinished() == true && (hx < 800 && hy > 0)) { // si termina
																	// el
																	// contador
			Hoja hoja0 = new Hoja();
			if (hojas.size() < 1) {// si es la primera
				hoja0.nacer(hx, hy);
			} else {// si es el resto, veo la anterior
				hx = hojas.get(hojas.size() - 1).x + p5.random(12, 100);
				hy = hojas.get(hojas.size() - 1).y + p5.random(-100, 5);

				hoja0.nacer(hx, hy);// la lleno
			}
			if (hx < 800 && hy > 0) {// si esta dentro del recuadro
				hojas.add(hoja0);// agrego una hoja
				//p5.println(hojas.size());
				tempo.start();// reinicio el contador
			} else {
				termino = true;
			}
		}
		if (termino == true) {
			p5.fill(0);
			p5.textSize(100);
			p5.textAlign(p5.LEFT);
			p5.text("HEDERA", p5.width / 2, p5.height / 2);
			p5.textSize(10);

		}
	}

}