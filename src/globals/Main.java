package globals;

import Lights.LightsManager;
import processing.core.PApplet;

public class Main extends PApplet {

	ClipManager clipManager;
	LightsManager lights;

	public void setup() {
		size(1024, 768,P2D);
		setPAppletSingleton();

		textureMode(NORMAL);
		
		lights = new LightsManager();
		lights.setup();
		
		clipManager = new ClipManager(lights);

	}

	public void draw() {
		frame.setTitle(Integer.toString(((int)frameRate)) + " fps");
		background(25,25,50);
		drawBackLines();
		
		clipManager.update();
		clipManager.render();
		lights.update();
		//lights.drawCalibration();
	}

	private void drawBackLines() {
		stroke(200);
		float offset = frameCount % 40;
		for (int i = 0; i < width; i+=40) {
			line(i + offset,0,i + offset,height);
		}
		
	}

	public void keyPressed() {

		clipManager.onKeyPressed(key);
		lights.onKeyPressed(key);

		if (key == 'e') {
			clipManager.toggleEditMode();
			lights.toggleEditMode();
		}

		if (key == CODED) {
			if (keyCode == UP) {
			}
		}

	}

	public void mousePressed() {

	}

	public void mouseReleased() {
	}

	public void mouseClicked() {
	}

	public void mouseDragged() {
		// ship.onMouseDragged();
	}

	public void mouseMoved() {
		// ship.onMouseMoved();
	}

	//*******************************************
	//*******************************************
	
	public static void main(String args[]) {
		/*
		 * if (args.length > 0) { String memorySize = args[0]; }
		 */

		PApplet.main(new String[] { Main.class.getName() });
		// PApplet.main(new String[] { "--present", Main.class.getName() }); //
		// PRESENT MODE
	}

	private void setPAppletSingleton() {
		PAppletSingleton.getInstance().setP5Applet(this);
	}

}
