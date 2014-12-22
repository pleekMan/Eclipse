package globals;

import processing.core.PApplet;

public class Main extends PApplet {

	ClipManager clipManager;

	public void setup() {
		size(1024, 768);
		setPAppletSingleton();

		clipManager = new ClipManager();
	}

	public void draw() {
		//background(25,25,50);
		clipManager.update();
	}

	public void keyPressed() {

		clipManager.onKeyPressed(key);

		if (key == 'r') {
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
