package globals;

import java.util.ArrayList;

import clips.cells.Cells;
import controlP5.Toggle;
import processing.core.PGraphics;
import processing.core.PImage;
import ClipsTest.CircleBounce.CircleBounce;
import ClipsTest.Hedera.Hedera;
import ClipsTest.LineColor.LineColor;
import ClipsTest.RectBounce.RectBounce;
import ClipsTest.VideoTest.VideoTest;
import Lights.LightsManager;

public class ClipManager {

	Main p5;
	ArrayList<Clip> clips;
	public int selectedClip;
	public int playingClip;

	LightsManager lights;

	boolean editMode;
	boolean showLightLayer;
	
	PImage mask;

	public ClipManager(LightsManager _lightManager) {
		p5 = getP5();

		clips = new ArrayList<Clip>();

		selectedClip = playingClip = 0;

		lights = _lightManager;

		editMode = false;
		showLightLayer = false;
		
		mask = p5.loadImage("OctagonaMask.png");
	}

	public void setup() {

	}

	public void update() {
		for (Clip clip : clips) {
			if (clip.isPlaying()) {
				clip.updateProjection();
				clip.updateLights();
				clip.resetTriggers();

				// PGraphics layer1 = clip.getProjectionLayer();
				// p5.image(layer1,0,0);
			}
		}
	}

	public void render() {
		for (Clip clip : clips) {
			if (clip.isPlaying()) {
				clip.renderProjection();

				if (showLightLayer) {
					clip.renderLights();
				}
			}
		}
		
		// RENDER MASK
		p5.image(mask, LightsManager.center.x, LightsManager.center.y, LightsManager.getBoundingBoxDimension(), LightsManager.getBoundingBoxDimension());

		// EDIT MODE DISPLAY -------------------------------

		if (editMode) {

			p5.textSize(12);

			// BACKPLANE
			p5.fill(0, 200);
			p5.stroke(255);
			p5.rect(0, 0, 250, p5.height);

			p5.fill(255);
			p5.noStroke();

			p5.text("FR: " + p5.frameRate, 20, 20);
			p5.text("Selected Clip: " + selectedClip, 20, 40);
			p5.text("Playing Clip: " + playingClip + "/" + clips.size(), 20, 60);

			drawClipNavigator();

			// DISPLAY WHICH LAYER IS BEING USED FOR THE LIGHTS
			if (clips.size() > 0) {
				if (getPlayingClip().useProjectionForLights) {
					p5.text("Light Layer: - PROJECTION", 20, 80);
				} else {
					p5.text("Light Layer: - LIGHTS", 20, 80);

				}
			}

		}

	}

	private void drawClipNavigator() {
		int originX = 20;
		int originY = 140;

		if (clips.size() > 0) {

			float boxSize = 200f / clips.size();

			p5.stroke(0, 200, 200);
			for (int i = 0; i < clips.size(); i++) {
				
				if (i == playingClip) {
					p5.fill(200,0,0);
				} else if (i == selectedClip){
					p5.fill(200,200,0);
				} else {
					p5.fill(127);
				}
				
				float x = originX + (boxSize * i);
				p5.rect(x, originY, boxSize, 40);
			}
			
			// PLAYING CLIP NAME
			p5.fill(200,0,0);
			p5.stroke(200,0,0);
			String playingClipName = getPlayingClip().getName();
			p5.text(playingClipName, 20, originY - 20);
			
			p5.line(originX + (playingClip * boxSize), originY, originX + (playingClip * boxSize), originY - 18);
			p5.line(originX, originY - 18, originX + (playingClip * boxSize), originY - 18);
	
			
			// SELECTED CLIP NAME
			String selectedClipName = getSelectedClip().getName();
			p5.text(selectedClipName, 20, originY + 60);
			
			
		} else {
			p5.text("-- NO CLIPS LOADED --", originX, originY);
		}
	}

	public void onKeyPressed(char key) {

		// SELECT AND LOAD CLIPS
		switch (key) {
		case '1':
			
			Cells cellIntro = new Cells();
			cellIntro.load();
			cellIntro.setName("Cell Intro");
			clips.add(cellIntro);
			System.out.println("Loaded :: " + Cells.class.getName());

			/*
			CircleBounce circleBounce = new CircleBounce();
			circleBounce.load();
			circleBounce.setName("CIRCLE");
			clips.add(circleBounce);
			System.out.println("Loaded :: " + CircleBounce.class.getName());
			*/
			break;
		case '2':
			VideoTest video = new VideoTest();
			video.load();
			video.setName("VIDEO");
			clips.add(video);
			System.out.println("Loaded :: " + VideoTest.class.getName());
			break;
		case '3':
			Hedera hiedra = new Hedera();
			hiedra.load();
			hiedra.setName("HEDERA");
			clips.add(hiedra);
			System.out.println("Loaded :: " + Hedera.class.getName());
			break;
		case '4':
			LineColor linea = new LineColor();
			linea.load();
			linea.setName("LINEA LOQUIS");
			clips.add(linea);
			System.out.println("Loaded :: " + LineColor.class.getName());
			break;

		default:
			// System.out.println("No Clip Found at: " + selectedClip);
			break;
		}

		// START SELECTED CLIP
		if (key == 'a') {
			if (clips.size() > 0) {
				triggerClip(selectedClip);
			}
		}
		if (key == 's') {
			if (clips.size() > 0) {
				getSelectedClip().stop();
			}
		}

		if (key == 'w') {
			goToNextClip();
		}
		if (key == 'q') {
			goToPreviousClip();
		}

		if (key == 'l') {
			toggleLightLayer();
		}
		
		// TRIGGERS
		if (key == 'z') {
			clips.get(playingClip).trigger(0);
		}
		if (key == 'x') {
			clips.get(playingClip).trigger(1);
		}
		if (key == 'c') {
			clips.get(playingClip).trigger(2);
		}
		if (key == 'v') {
			clips.get(playingClip).trigger(3);
		}
		if (key == 'b') {
			clips.get(playingClip).trigger(4);
		}
	}

	private void triggerClip(int selectedClip) {

		if (selectedClip < clips.size()) {

			for (Clip clip : clips) {
				clip.stop();
			}

			lights.bindToLightLayer(getSelectedClip().getLightsLayer());
			getSelectedClip().start();
			playingClip = selectedClip;

			// UPDATE THE_USE_PROJECTION_FOR_LIGHTS_TOGGLE
			Toggle toggle = (Toggle) p5.cp5.getController("Use_Projection_For_Lights");
			toggle.setState(getPlayingClip().useProjectionForLights);

		} else {
			System.out.println("No Clip Found at: " + selectedClip);
		}
	}

	public void toggleEditMode() {
		editMode = !editMode;
	}

	public void toggleLightLayer() {
		showLightLayer = !showLightLayer;
	}

	public void useProjectionForLights(boolean state) {
		// TODO HACERLO BIEN
		if (clips.size() > 0) {
			getPlayingClip().useProjectionForLights(state);
			lights.bindToLightLayer(getPlayingClip().getLightsLayer());
		}
	}

	public Clip getPlayingClip() {
		return clips.get(playingClip);
	}

	public Clip getSelectedClip() {
		return clips.get(selectedClip);
	}

	public void goToNextClip() {
		selectedClip++;
		if (selectedClip > clips.size() - 1) {
			selectedClip = clips.size() - 1;
		}
	}

	public void goToPreviousClip() {
		selectedClip--;
		if (selectedClip < 0) {
			selectedClip = 0;
		}
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
