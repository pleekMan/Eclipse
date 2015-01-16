package ClipsTest.Hedera;
import globals.Main;
import globals.PAppletSingleton;

class Hoja {

	Main p5;

	int diamt = 25;
	float x, y;
	float x0, y0;

	Hoja() {
		p5 = getP5();
	}

	void nacer(float x0, float y0) {// tipico de prog de objetos muau
		x = x0;// se llama setter, setear algo muau
		y = y0;

	}

	void dibujar() {
		p5.noStroke();
		p5.fill(37, 245, 49);
		p5.ellipse(x, y, diamt, diamt);
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}

}
