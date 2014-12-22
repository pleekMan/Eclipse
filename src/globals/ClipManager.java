package globals;

import java.util.ArrayList;

import Clips.CircleBounce.CircleBounce;
import Clips.RectBounce.RectBounce;
import Clips.Hedera.Hedera;
import Clips.LineColor.LineColor;
import Lights.LightsManager;


public class ClipManager {

	Main p5;
	ArrayList<Clip> clips;
	int selectedClip;
	
	LightsManager lights;

	public ClipManager(LightsManager _lightManager) {
		p5 = getP5();

		clips = new ArrayList<Clip>();
		
		selectedClip = 0;
		
		lights = _lightManager;
	}

	public void setup() {

	}

	public void update() {
		for (Clip clip : clips) {
			if (clip.isPlaying()) {
				clip.update();
				clip.render();
			}
		}
		
		p5.fill(255,0,0);
		p5.text("Selected Clip: " + selectedClip, 20, 20);
	}

	public void render() {

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
			RectBounce rectBounce = new RectBounce();
			rectBounce.load();
			clips.add(rectBounce);
			System.out.println("Loaded :: " + RectBounce.class.getName());
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
			//lights.bindToLightLayer(linea.getLightsLayer());
			clips.add(linea);
			System.out.println("Loaded :: " + LineColor.class.getName());
			break;

		default:
			//System.out.println("No Clip Found at: " + selectedClip);
			break;
		}
		
		// START SELECTED CLIP
		if(key == 'a'){
			if (clips.size() > 0 ) {
				triggerClip(selectedClip);
			}
		}
		if(key == 's'){
			if (clips.size() > 0 ) {
				clips.get(selectedClip).stop();
			}
		}
		
		if(key == 'w'){
			selectedClip++;
			//if (selectedClip > clips.size() - 1) {
				//selectedClip = clips.size() - 1;
			//}
		}
		if(key == 'q'){
			selectedClip--;
			if (selectedClip < 0) {
				selectedClip = 0;
			}
		}
	}

	private void triggerClip(int selectedClip) {
		
		if (selectedClip < clips.size()) {
			
			for (Clip clip : clips) {
				clip.stop();
			}
			
			lights.bindToLightLayer(clips.get(selectedClip).getLightsLayer());
			clips.get(selectedClip).start();
		
		} else {
			System.out.println("No Clip Found at: " + selectedClip);
		}
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
