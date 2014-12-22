package Clips.CircleBounce;

import processing.core.PGraphics;
import globals.Clip;
import globals.Main;
import globals.PAppletSingleton;

public class CircleBounce extends Clip {

	float diameter;
	float velocity;
	boolean isPlaying;

	public CircleBounce() {
		super();
	}

	@Override
	public void load() {

		super.load();

		diameter = 1;
		velocity = 2;
		isPlaying = false;
	}

	@Override
	public void update() {
		diameter += velocity;
		if (diameter > p5.height || diameter < 0)
			velocity *= -1;
	}

	@Override
	public void render() {
		projection.beginDraw();

		projection.background(255);
		projection.noFill();
		projection.stroke(0);
		projection.ellipse(p5.width * 0.5f, p5.height * 0.5f, diameter, diameter);

		//if (p5.frameCount % 100 == 0) {
			projection.fill(p5.random(255),p5.random(255),p5.random(255));
		
		projection.ellipse(p5.mouseX, p5.mouseY, 50, 50);

		projection.endDraw();

		p5.image(projection, 0, 0);

	}

	public PGraphics getProjectionLayer() {
		return projection;
	}

	public PGraphics getLightsLayer() {
		// IN THIS CASE, PROJECTION AND LIGHT LAYER ARE THE SAME
		return projection;
	}

}
