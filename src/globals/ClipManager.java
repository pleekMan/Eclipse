package globals;

import java.util.ArrayList;

import processing.core.PGraphics;
import Clips.CircleBounce.CircleBounce;
import Clips.RectBounce.RectBounce;
import Clips.VideoTest.VideoTest;
import Clips.Hedera.Hedera;
import Clips.LineColor.LineColor;
import Lights.LightsManager;

public class ClipManager {

	Main p5;
	ArrayList<Clip> clips;
	int selectedClip;
	int playingClip;

	LightsManager lights;

	boolean editMode;
	boolean showLightLayer;

	public ClipManager(LightsManager _lightManager) {
		p5 = getP5();

		clips = new ArrayList<Clip>();

		selectedClip = playingClip = 0;

		lights = _lightManager;

		editMode = false;
		showLightLayer = false;
	}

	public void setup() {

	}

	public void update() {
		for (Clip clip : clips) {
			if (clip.isPlaying()) {
				clip.updateProjection();
				clip.updateLights();

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
		

		if (editMode) {

			p5.textSize(12);

			// BACKPLANE
			p5.fill(0, 200);
			p5.stroke(255);
			p5.rect(0, 0, 250, p5.height);

			p5.fill(255);
			p5.noStroke();

			p5.text("FR: " + p5.frameRate, 10, 20);
			p5.text("Selected Clip: " + selectedClip, 10, 40);
			p5.text("Playing Clip: " + playingClip + "/" + clips.size(), 10, 60);

		}

	}

	public void onKeyPressed(char key) {

		// SELECT AND LOAD CLIPS
		switch (key) {
		case '1':
			CircleBounce circleBounce = new CircleBounce();
			circleBounce.load();
			clips.add(circleBounce);
			System.out.println("Loaded :: " + CircleBounce.class.getName());
			break;
		case '2':
			VideoTest video = new VideoTest();
			video.load();
			clips.add(video);
			System.out.println("Loaded :: " + VideoTest.class.getName());
			break;
		case '3':
			Hedera hiedra = new Hedera();
			hiedra.load();
			clips.add(hiedra);
			System.out.println("Loaded :: " + Hedera.class.getName());
			break;
		case '4':
			LineColor linea = new LineColor();
			linea.load();
			// lights.bindToLightLayer(linea.getLightsLayer());
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
				clips.get(selectedClip).stop();
			}
		}

		if (key == 'w') {
			selectedClip++;
			// if (selectedClip > clips.size() - 1) {
			// selectedClip = clips.size() - 1;
			// }
		}
		if (key == 'q') {
			selectedClip--;
			if (selectedClip < 0) {
				selectedClip = 0;
			}
		}

		if (key == 'l') {
			toggleLightLayer();
		}
	}

	private void triggerClip(int selectedClip) {

		if (selectedClip < clips.size()) {

			for (Clip clip : clips) {
				clip.stop();
			}

			lights.bindToLightLayer(clips.get(selectedClip).getLightsLayer());
			clips.get(selectedClip).start();
			playingClip = selectedClip;

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

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
