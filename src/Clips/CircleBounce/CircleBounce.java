package Clips.CircleBounce;

import processing.core.PGraphics;
import globals.Clip;

public class CircleBounce extends Clip {

	float diameter;
	float velocity;

	public CircleBounce() {
		super();
	}

	@Override
	public void load() {

		super.load();

		diameter = 1;
		velocity = 2;
	}

	@Override
	public void updateProjection() {

		diameter += velocity;
		if (diameter > p5.height || diameter < 0)
			velocity *= -1;

		projection.beginDraw();

		projection.background(255);
		projection.noFill();
		projection.stroke(0);
		projection.ellipse(p5.width * 0.5f, p5.height * 0.5f, diameter, diameter);

		// if (p5.frameCount % 100 == 0) {
		projection.fill(p5.random(255), p5.random(255), p5.random(255));

		projection.ellipse(p5.mouseX, p5.mouseY, 50, 50);

		projection.endDraw();

	}

	@Override
	public void updateLights() {

		lights.beginDraw();
		lights.background(0);

		lights.fill(255, 0, 255);
		lights.stroke(255);
		lights.strokeWeight(4);

		lights.ellipse(p5.width * 0.5f, p5.height * 0.5f, p5.height - diameter, p5.height - diameter);

		lights.endDraw();

	}

	@Override
	public void renderProjection() {
		
		/*
		p5.beginShape();
		p5.texture(projection);
		p5.vertex(0, 0, 0, 0);
		p5.vertex(projection.width, 0, 1, 0);
		p5.vertex(projection.width, projection.height, 1, 1);
		p5.vertex(0, projection.height, 0, 1);
		p5.endShape(p5.CLOSE);
		*/
		p5.image(projection, 0, 0);

	}

	@Override
	public void renderLights() {
		p5.image(lights, 0, 0);
	}

	@Override
	public PGraphics getProjectionLayer() {
		return projection;
	}

	@Override
	public PGraphics getLightsLayer() {
		return lights;
	}

}
