package Clips.LineColor;

import globals.Clip;
import globals.Main;
import globals.PAppletSingleton;

public class LineColor extends Clip {

	float largo;
	int color;
	float angle;
	float angleIncrement;

	boolean isPlaying;

	public LineColor() {
		super();
	}

	@Override
	public void load() {
		super.load();
		largo = 10;
		color = 0;
		angle = 0;
		angleIncrement = 0.01f;
	}

	@Override
	public void update() {
		angle += angleIncrement;
		color += (int)(angleIncrement * 100);
		largo += (int)(angleIncrement * 1000);
		
		if (color > 255) {
			color = 0;
		}
		if (largo > p5.width) {
			largo = 10;
		}
	}

	@Override
	public void render() {
		p5.pushMatrix();
		p5.translate(p5.width * 0.5f, p5.height * 0.5f);
		p5.rotate(angle);
		
		p5.stroke(color);
		p5.line(-largo * 0.5f, 0, largo * 0.5f, 0);
		p5.popMatrix();
	}

	@Override
	public Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}

}
