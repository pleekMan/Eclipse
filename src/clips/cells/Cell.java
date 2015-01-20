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

	AniSequence[] animation;

	boolean isFreakingOut;
	boolean isInLine;
	boolean isInLineTriangle;

	public Cell(PGraphics drawLayer) {
		p5 = getP5();
		projectionLayer = drawLayer;

		x = p5.random(200, projectionLayer.width - 200);
		y = p5.random(200, projectionLayer.height - 200);
		size = 0;
		color = p5.color(p5.random(255), p5.random(255), p5.random(255));
		vertexCount = (int) p5.random(4, 9);
		angleStep = p5.TWO_PI / vertexCount;
		rotation = p5.random(p5.TWO_PI);

		animation = new AniSequence[3];
		for (int i = 0; i < animation.length; i++) {
			animation[i] = new AniSequence(p5);
		}
		setupAnimation();

		isFreakingOut = false;
		isInLine = false;
		isInLineTriangle = false;

	}

	public void setDrawLayer(PGraphics _layer) {
		projectionLayer = _layer;
	}

	public void start() {
		// animation.resume();
	}

	public void stop() {
		// animation.pause();
	}

	public void update() {

	}

	public void render() {

		projectionLayer.noFill();
		projectionLayer.stroke(color);

		projectionLayer.pushMatrix();
		projectionLayer.translate(x, y);
		projectionLayer.rotate(rotation);

		if (!isInLineTriangle) { // WHILE NOT IN LINE as triangle

			projectionLayer.strokeWeight(1);

			// CELL BODY
			projectionLayer.beginShape();

			for (int i = 0; i < vertexCount; i++) {
				float vX = ((size * 0.5f) * p5.cos(angleStep * i));
				float vY = ((size * 0.5f) * p5.sin(angleStep * i));
				projectionLayer.vertex(vX, vY);
			}

			projectionLayer.endShape(p5.CLOSE);

		} else { // WHILE IN LINE

			projectionLayer.strokeWeight(3);

			projectionLayer.stroke(0, 255, 255);

			projectionLayer.triangle(0, -(size * 0.5f), size * 0.5f, size * 0.5f, -(size * 0.5f), size * 0.5f);

		}

		if (isFreakingOut) {
			if (p5.frameCount % (int) p5.random(30, 45) == 0) {
				// for (int i = 0; i < 2; i++) {
				projectionLayer.strokeWeight(p5.random(2, 6));

				projectionLayer.translate(p5.random(-20, 20), p5.random(-20, 20));
				//projectionLayer.stroke(0, 255 * (1 - animation[1].getSeek()), 255 * (1 - animation[1].getSeek()));
				projectionLayer.stroke(0, 255, 255);

				
				projectionLayer.triangle(0, -(size * 0.5f), size * 0.5f, size * 0.5f, -(size * 0.5f), size * 0.5f);
				// }

			}

			// SPARKS

			if (p5.frameCount % 5 == 0) {

				projectionLayer.stroke(0,255,255);

				for (int i = 0; i < 5; i++) {


					float sparkX = p5.random(-size, size);
					float sparkY = p5.random(-size, size);

					float lineX = sparkX + p5.random(-5, 5);
					float lineY = sparkY + p5.random(-5, 5);

					projectionLayer.line(sparkX, sparkY, lineX, lineY);
				}
			}

		} else if (isInLine) {
			x += 5;
			if (x > projectionLayer.width * 0.5f) {
				isInLineTriangle = true;
				size = 100;
				rotation = 0;
				y = projectionLayer.height * 0.5f;
			} else {
				isInLineTriangle = false;
			}
			
			if (x > projectionLayer.width + size) {
				size = p5.random(20, 400);
				x = - size;
			}
		}

		projectionLayer.popMatrix();

	}

	private void setupAnimation() {

		AniSequence intro = animation[0];
		AniSequence freakOut = animation[1];

		// INTRO - BEGIN

		int birthTime = (int) p5.random(1, 2);
		int maxSize = (int) p5.random(20, 200);

		intro.beginSequence();

		// Ani.to(this (class), duration (seconds), "variableToAffect",
		// targetValue, Ani.CUBIC_IN_OUT (EASING TYPE))

		// step 0 - BiRTH
		intro.beginStep();
		intro.add(p5.ani.to(this, birthTime, "size", maxSize, Ani.CUBIC_IN_OUT));
		intro.add(p5.ani.to(this, birthTime, "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		intro.endStep();

		// step 1
		// TODO ASSIGNING DIFFERENT EASINGS TO X,Y CREATES A MORE ORGANIC MOTION
		// String position = "x:" + p5.random(birthSize, projectionLayer.width -
		// birthSize) + ",y:" + p5.random(birthSize, projectionLayer.height -
		// birthSize);
		float randomX = p5.random(maxSize, projectionLayer.width - maxSize);
		float randomY = p5.random(maxSize, projectionLayer.height - maxSize);
		float randomSize = p5.random(maxSize * 0.1f, maxSize);

		intro.beginStep();
		intro.add(p5.ani.to(this, p5.random(5, 20), "x", randomX, Ani.ELASTIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "y", randomY, Ani.BACK_IN_OUT));
		// animation.add(p5.ani.to(this, p5.random(5, 20), position,
		// Ani.ELASTIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "size", randomSize, Ani.BOUNCE_IN_OUT));

		intro.endStep();

		// animation.add(p5.ani.to(this, p5.random(3), "x:100,y:100",
		// Ani.CUBIC_IN_OUT));

		// step 2
		randomX = p5.random(maxSize, projectionLayer.width - maxSize);
		randomY = p5.random(maxSize, projectionLayer.height - maxSize);
		randomSize = p5.random(maxSize * 0.1f, maxSize);

		intro.beginStep();
		intro.add(p5.ani.to(this, p5.random(5, 20), "x", randomX, Ani.BACK_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "y", randomY, Ani.ELASTIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "size", randomSize, Ani.BOUNCE_IN_OUT));
		intro.endStep();

		// step 3

		randomX = p5.random(maxSize, projectionLayer.width - maxSize);
		randomY = p5.random(maxSize, projectionLayer.height - maxSize);
		randomSize = p5.random(maxSize * 0.1f, maxSize);

		intro.beginStep();
		intro.add(p5.ani.to(this, p5.random(5, 20), "x", randomX, Ani.ELASTIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "y", randomY, Ani.BACK_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "rotation", p5.random(p5.TWO_PI), Ani.CUBIC_IN_OUT));
		intro.add(p5.ani.to(this, p5.random(5, 20), "size", randomSize, Ani.BOUNCE_IN_OUT));
		intro.endStep();

		// STEP END
		intro.beginStep();
		// animation.add(p5.ani.to(this, 2, "x:400,y:400", Ani.CUBIC_IN_OUT));
		intro.add(p5.ani.to(this, 2, "size", 0, Ani.CUBIC_IN_OUT, "onEnd:introAnimationEnd"));
		intro.add(p5.ani.to(this, 2, "rotation", p5.random(p5.TWO_PI), Ani.BACK_IN_OUT));
		intro.endStep();

		intro.endSequence();

		// start the whole sequence
		intro.start();

		// INTRO - END

		// FREAK OUT - BEGIN
		
		float inLineX = p5.random(10, projectionLayer.width * 0.4f);
		float inLineY = projectionLayer.height * 0.5f;
		int time = 25;

		freakOut.beginSequence();
		freakOut.beginStep();
		freakOut.add(p5.ani.to(this, time, "x", inLineX, Ani.EXPO_IN_OUT));
		freakOut.add(p5.ani.to(this, time, "y", inLineY, Ani.QUINT_IN));
		freakOut.add(p5.ani.to(this, time, "rotation", 0, Ani.BACK_IN_OUT));
		freakOut.endStep();
		
		/*
		freakOut.beginStep();
		freakOut.add(p5.ani.to(this, 2, "size", 0, Ani.EXPO_IN));
		freakOut.endStep();
		*/
		
		freakOut.endSequence();
		
		// FREAK OUT - END
	}

	public void introAnimationEnd() {
		// println("sequenceEnd() restart all again");
		animation[0].start();
		color = p5.color(p5.random(255), p5.random(255), p5.random(255));
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
