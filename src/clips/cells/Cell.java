package clips.cells;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniSequence;
import processing.core.PGraphics;
import globals.Main;
import globals.PAppletSingleton;

public class Cell {

	Main p5;
	PGraphics projectionLayer;

	float x, y;
	// float velX, velY;
	int vertexCount;
	float angleStep;
	float size;
	int color;
	
	float rotation;

	AniSequence animation;

	public Cell(PGraphics drawLayer) {
		p5 = getP5();
		projectionLayer = drawLayer;

		x = p5.random(projectionLayer.width);
		y = p5.random(projectionLayer.height);
		size = 0;
		color = p5.color(p5.random(255), p5.random(255), p5.random(255));
		vertexCount = (int) p5.random(4, 9);
		angleStep = p5.TWO_PI / vertexCount;
		rotation = p5.random(p5.TWO_PI);

		animation = new AniSequence(p5);
		setupAnimation();

	}

	public void setDrawLayer(PGraphics _layer) {
		projectionLayer = _layer;
	}

	public void start() {
		animation.resume();
	}

	public void stop() {
		animation.pause();
	}

	public void update() {

	}

	public void render() {

		projectionLayer.noFill();
		projectionLayer.stroke(color);

		projectionLayer.pushMatrix();
		projectionLayer.translate(x, y);
		projectionLayer.rotate(rotation);
		projectionLayer.beginShape();

		for (int i = 0; i < vertexCount; i++) {
			float vX = ((size * 0.5f) * p5.cos(angleStep * i));
			float vY = ((size * 0.5f) * p5.sin(angleStep * i));
			projectionLayer.vertex(vX, vY);
		}

		projectionLayer.endShape(p5.CLOSE);
		
		projectionLayer.popMatrix();

	}

	private void setupAnimation() {

		int birthTime = (int) p5.random(5, 20);
		int birthSize = (int) p5.random(20, 200);

		animation.beginSequence();

		// Ani.to(this (class), duration (seconds), "variableToAffect",
		// targetValue, Ani.CUBIC_IN_OUT (EASING TYPE))

		// step 0 - BiRTH
		animation.beginStep();
		animation.add(p5.ani.to(this, birthTime, "size", birthSize, Ani.CUBIC_IN_OUT));
		animation.add(p5.ani.to(this, birthTime, "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		animation.endStep();

		// step 1
		String position = "x:" + p5.random(birthSize, projectionLayer.width - birthSize) + ",y:" + p5.random(birthSize, projectionLayer.height - birthSize);
		animation.beginStep();
		animation.add(p5.ani.to(this, p5.random(5, 20), position, Ani.CUBIC_IN_OUT));
		animation.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		animation.endStep();

		// animation.add(p5.ani.to(this, p5.random(3), "x:100,y:100",
		// Ani.CUBIC_IN_OUT));

		// step 2
		position = "x:" + p5.random(birthSize, projectionLayer.width - birthSize) + ",y:" + p5.random(birthSize, projectionLayer.height - birthSize);
		animation.beginStep();
		animation.add(p5.ani.to(this, p5.random(5, 20), position, Ani.CUBIC_IN_OUT));
		animation.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		animation.endStep();

		// step 3
		position = "x:" + p5.random(birthSize, projectionLayer.width - birthSize) + ",y:" + p5.random(birthSize, projectionLayer.height - birthSize);
		animation.beginStep();
		animation.add(p5.ani.to(this, p5.random(5, 20), position, Ani.CUBIC_IN_OUT));
		animation.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		animation.endStep();

		animation.beginStep();
		//animation.add(p5.ani.to(this, 2, "x:400,y:400", Ani.CUBIC_IN_OUT));
		animation.add(p5.ani.to(this, 2, "size", 0, Ani.CUBIC_IN_OUT, "onEnd:animationEnd"));
		animation.add(p5.ani.to(this, 2, "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		animation.endStep();


		animation.endSequence();

		// start the whole sequence
		// animation.start();
	}

	public void animationEnd() {
		// println("sequenceEnd() restart all again");
		animation.start();
		color = p5.color(p5.random(255), p5.random(255), p5.random(255));
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
