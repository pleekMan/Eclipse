package ClipsTest.LineColor;

import processing.core.PGraphics;
import globals.Clip;
import globals.Main;
import globals.PAppletSingleton;

public class LineColor extends Clip {

	float largo;
	int color;
	float angle;
	float angleIncrement;


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
	public void updateProjection() {

		angle += angleIncrement;
		color += (int) (angleIncrement * 100);
		largo += (int) (angleIncrement * 1000);

		if (color > 255) {
			color = 0;
		}
		if (largo > p5.width) {
			largo = 10;
		}

		projection.beginDraw();

		projection.pushMatrix();
		projection.translate(p5.width * 0.5f, p5.height * 0.5f);
		projection.rotate(angle);

		projection.stroke(color);
		projection.line(-largo * 0.5f, 0, largo * 0.5f, 0);
		projection.popMatrix();

		projection.endDraw();
	}
	
	@Override
	public void updateLights(){
		lights.beginDraw();
		
		if (p5.frameCount % 20 < 10) {
			lights.background(0);
		} else {
			lights.background(255,255,0);
		}
		lights.endDraw();
	}

	@Override
	public void renderProjection() {
		p5.image(projection, 0, 0);
	}
	
	@Override
	public void renderLights(){
		p5.image(lights, 0, 0);
	}

	/*
	public PGraphics getProjectionLayer() {
		return projection;
	}

	public PGraphics getLightsLayer() {
		return lights;
	}
	*/

	@Override
	public Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}

}
