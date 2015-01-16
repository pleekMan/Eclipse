package clips.cells;

import java.util.ArrayList;

import Lights.LightsManager;
import globals.Clip;

public class Cells extends Clip {

	float diameter;
	float velocity;
	
	ArrayList<Cell> cells;

	public Cells() {
		super();
	}

	@Override
	public void load() {

		super.load();
		
		cells = new ArrayList<Cell>();
		for (int i = 0; i < 10; i++) {
			Cell newCell = new Cell(projection);
			cells.add(newCell);
		}
		projection.beginDraw();
		projection.background(0);
		projection.endDraw();

	}
	
	@Override
	public void start(){
		super.start();
		for (Cell actualCell : cells) {
			actualCell.start();
		}
	}
	
	@Override
	public void stop(){
		super.stop();
		for (Cell actualCell : cells) {
			actualCell.start();
		}
	}

	@Override
	public void updateProjection() {

		projection.beginDraw();
		projection.noStroke();
		projection.fill(0,1);
		projection.rect(0, 0, projection.width, projection.height);
		for (Cell actualCell : cells) {
			actualCell.render();
		}

		projection.endDraw();

	}

	@Override
	public void updateLights() {

		lights.beginDraw();
		
		lights.endDraw();

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

