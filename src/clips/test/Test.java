package clips.test;

import Lights.LightsManager;
import globals.Clip;

public class Test extends Clip {
	
	public Test(){
		super();
	}
	
	@Override
	public void load(){
		super.load();
	}
	
	@Override
	public void start(){
		super.start();
	}
	
	@Override
	public void stop(){
		super.stop();
	}
	
	@Override
	public void updateProjection(){
		projection.beginDraw();
		projection.background(0);
		
		float x = p5.map(p5.mouseX, LightsManager.center.x - (LightsManager.getBoundingBoxDimension() * 0.5f), LightsManager.center.x + (LightsManager.getBoundingBoxDimension() * 0.5f), 0, LightsManager.getBoundingBoxDimension());
		float y = p5.map(p5.mouseY, LightsManager.center.y - (LightsManager.getBoundingBoxDimension() * 0.5f), LightsManager.center.y + (LightsManager.getBoundingBoxDimension() * 0.5f), 0, LightsManager.getBoundingBoxDimension());

		projection.noStroke();
		projection.fill(255);
		projection.ellipse(x,y,30,30);
		
		projection.endDraw();
	}
	
	@Override
	public void updateLights(){
		//lights.beginDraw();
		
		//lights.endDraw();
	}
	
	@Override
	public void renderProjection(){
		p5.pushMatrix();
		p5.translate(LightsManager.center.x, LightsManager.center.y);
		p5.image(projection, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
		p5.popMatrix();
	}
	
	@Override
	public void renderLights(){
		
		if (!useProjectionForLights) {
			p5.pushMatrix();
			p5.translate(LightsManager.center.x, LightsManager.center.y);
			p5.image(lights, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
			p5.popMatrix();
		}
	}

}
