package ClipsTest.VideoTest;

import org.gstreamer.lowlevel.BaseSrcAPI.Create;

import Lights.LightsManager;
import processing.core.*;
import globals.Clip;
import processing.video.*;
import processing.opengl.*;

public class VideoTest extends Clip {

	Movie video;
	PImage videoTransfer;

	public VideoTest() {
		super();
	}

	@Override
	public void load() {

		super.load();

		// TODO PARAMETERIZE VIDEO PATH
		video = new Movie(p5, p5.sketchPath + "/bin/Clips/VideoTest/Test01.mov");
		//video = new Movie(p5, p5.sketchPath + "/bin/Clips/VideoTest/Test01.mov");
		video.pause();
		
		videoTransfer = p5.createImage(video.width, video.height, p5.ARGB);

	}

	@Override
	public void start() {
		super.start();
		video.loop();

	}

	@Override
	public void stop() {
		super.stop();
		video.pause();
	}

	@Override
	public void updateProjection() {

		try {
			if (video.available()) {
				video.read();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}

		videoTransfer = video.get();
		
		projection.beginDraw();
		projection.image(videoTransfer, 0, 0);
		projection.endDraw();

	}

	@Override
	public void updateLights() {

		//lights.beginDraw();

		//lights.endDraw();

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
		p5.pushMatrix();
		p5.translate(LightsManager.center.x, LightsManager.center.y);
		p5.image(lights, 0, 0, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());
		p5.popMatrix();
	}

	@Override
	public PGraphics getProjectionLayer() {
		return projection;
	}
	
	@Override
	public PGraphics getLightsLayer() {
		//TODO IMPLEMENT A hasLightLayer FOR ALL CLIPS TO AVOID PROCESSING UNNECESARY LAYER
		return projection;
	}

}
