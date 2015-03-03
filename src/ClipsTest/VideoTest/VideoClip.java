package ClipsTest.VideoTest;

import Lights.LightsManager;
import processing.core.*;
import globals.Clip;
import processing.video.*;
import processing.opengl.*;

public class VideoClip extends Clip {

	Movie videoFile;
	PImage videoTransfer;
	Movie lightsFile;
	PImage lightsTransfer;

	public VideoClip() {
		super();
	}

	//@Override
	public void load(String videoClipPath, String lightsClipPath) {

		super.load();

		String videoPath = p5.sketchPath + "/bin/clips/" + videoClipPath;
		String lightsPath = p5.sketchPath + "/bin/clips/" + lightsClipPath;

		videoFile = new Movie(p5, videoPath);
		videoFile.pause();
		videoTransfer = p5.createImage(videoFile.width,videoFile.height, p5.RGB);
		
		lightsFile = new Movie(p5, lightsPath);
		lightsFile.pause();
		lightsTransfer = p5.createImage(lightsFile.width,lightsFile.height, p5.RGB);

	}

	@Override
	public void start() {
		super.start();
		//videoFile.loop();
		//lightsFile.loop();
		videoFile.play();
		lightsFile.play();

	}

	@Override
	public void stop() {
		super.stop();
		videoFile.pause();
		lightsFile.pause();
	}

	@Override
	public void updateProjection() {

		try {
			if (videoFile.available()) {
				videoFile.read();
				
				videoTransfer = videoFile.get();
				
				projection.beginDraw();
				projection.image(videoTransfer, 0, 0, projection.width, projection.height);
				projection.endDraw();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		

	}

	@Override
	public void updateLights() {

		try {
			if (lightsFile.available()) {
				lightsFile.read();
				
				lightsTransfer = lightsFile.get();
				
				lights.beginDraw();
				lights.image(lightsTransfer, 0, 0, lights.width, lights.height);
				lights.endDraw();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		

	}

	@Override
	public void renderProjection() {
		p5.imageMode(p5.CENTER);
		p5.pushMatrix();
		p5.translate(LightsManager.center.x, LightsManager.center.y);
		p5.image(projection, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
		p5.popMatrix();

	}

	@Override
	public void renderLights() {
		p5.imageMode(p5.CENTER);
		p5.pushMatrix();
		p5.translate(LightsManager.center.x, LightsManager.center.y);
		p5.image(lights, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
		p5.popMatrix();
	}

	/*
	@Override
	public PGraphics getProjectionLayer() {
		return projection;
	}
	
	@Override
	public PGraphics getLightsLayer() {
		//TODO IMPLEMENT A hasLightLayer FOR ALL CLIPS TO AVOID PROCESSING UNNECESARY LAYER
		return lights;
	}
	*/

}
