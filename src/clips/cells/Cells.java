package clips.cells;

import java.util.ArrayList;

import Lights.LightsManager;
import globals.Clip;

public class Cells extends Clip {

	// TRIGGERS:
	// 0 : ADD CELL
	// 1 : CELLS START FREAKING OUT / LIGHTS FLASH
	// 2 : PRODUCTION LINE

	//float diameter;
	//float velocity;

	ArrayList<Cell> cells;
	
	float[] lightCirclesSize;
	
	public Cells() {
		super();
	}

	@Override
	public void load() {

		super.load();

		cells = new ArrayList<Cell>();

		lightCirclesSize = new float[10];
		float separation = lights.height / lightCirclesSize.length;
		for (int i = 0; i < lightCirclesSize.length; i++) {
			lightCirclesSize[i] = separation * i;;
		}
		
		projection.beginDraw();
		projection.background(0);
		projection.endDraw();
		
		lights.beginDraw();
		lights.background(0);
		lights.endDraw();

	}

	@Override
	public void start() {
		super.start();
		for (Cell actualCell : cells) {
			actualCell.start();
		}
	}

	@Override
	public void stop() {
		super.stop();
		for (Cell actualCell : cells) {
			actualCell.start();
		}
	}

	@Override
	public void updateProjection() {

		projection.beginDraw();
		projection.noStroke();
		
		if (cells.size() != 0 && cells.get(0).isInLine) {
			projection.fill(0, 5);
		} else {
			projection.fill(0, 1);
		}
		
		projection.rect(0, 0, projection.width, projection.height);
		for (Cell actualCell : cells) {
			actualCell.render();
		}

		projection.endDraw();

		if (triggers[0]) {
			addCells();
		} else if (triggers[1] && !cells.get(0).isFreakingOut) {
			for (Cell actualCell : cells) {
				actualCell.animation[0].pause();
				actualCell.animation[1].start();
				actualCell.isFreakingOut = true;
			}
		} else if (triggers[2]) {
			for (Cell actualCell : cells) {
				actualCell.animation[1].pause();
				actualCell.isFreakingOut = false;

				actualCell.isInLine = true;
			}
		}

	}

	@Override
	public void updateLights() {

		lights.beginDraw();

		lights.rectMode(p5.CORNER);
		lights.fill(0, 5);
		lights.rect(0, 0, lights.width, lights.height);

		if (triggers[1]) {
			//  DRAW DIAGONAL CROSS
			lights.fill(255);
			lights.rect(0, 0, lights.width, lights.height);
			lights.fill(0);
			lights.rect(lights.width * 0.5f - 20, 0, 40, lights.height - 20);
			lights.rect(0, lights.height * 0.5f - 20, lights.width, 40);
			
		} else if (cells.size() != 0 && cells.get(0).isInLine) {
			
			/*
			lights.stroke(255);
			//lights.strokeWeight(20);
			int circleCount = (int)(lights.height * 0.8);
			float offset = (p5.frameCount % circleCount) * 5;
			for (int i = 0; i < lights.height; i += circleCount) {
				lights.ellipse(lights.width * 0.5f, lights.height * 0.5f, i - offset, i - offset);
			}*/
			
			lights.background(0);
			lights.strokeWeight(40);
			
			for (int i = 0; i < lightCirclesSize.length; i++) {
				
				if (i % 2 == 0) {
					lights.stroke(0,255,255);
				} else {
					lights.stroke(0);
				}
				
				lights.ellipse(lights.width * 0.5f, lights.height * 0.5f, lightCirclesSize[i],lightCirclesSize[i]);
				
				lightCirclesSize[i] -= 10;
				
				if (lightCirclesSize[i] < 0) {
					lightCirclesSize[i] = lights.height;
				}
				
				
			}
			
			lights.noStroke();
			lights.fill(0);
			lights.rect(lights.width * 0.5f - 20, 0, 40, lights.height - 20);
			lights.rect(0, lights.height * 0.5f - 20, lights.width, 40);
			
			for (Cell actualCell : cells) {
				if (actualCell.x < (lights.width * 0.5f) + 10 && actualCell.x > (lights.width * 0.5f) - 10) {
					lights.fill(actualCell.color);
					lights.ellipse(lights.width * 0.5f, lights.height * 0.5f, 30, 30);
					break;
				}
			}
			
			//lights.strokeWeight(20);
			
		}

		lights.endDraw();

	}

	private void addCells() {
		for (int i = 0; i < 1; i++) {
			Cell newCell = new Cell(projection);
			cells.add(newCell);
		}
	}

	@Override
	public void renderProjection() {

		p5.pushMatrix();
		p5.translate(LightsManager.center.x, LightsManager.center.y);
		p5.image(projection, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
		p5.popMatrix();

	}

	@Override
	public void renderLights() {
		if (!useProjectionForLights) {
			p5.pushMatrix();
			p5.translate(LightsManager.center.x, LightsManager.center.y);
			p5.image(lights, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
			p5.popMatrix();
		}
	}

}
