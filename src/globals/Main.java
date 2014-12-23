package globals;

import controlP5.*;
import Lights.LightsManager;
import processing.core.PApplet;

public class Main extends PApplet {

	ClipManager clipManager;
	LightsManager lights;

	// GUI - BEGIN -----------------

	ControlP5 cp5;

	// GUI - END -------------------

	public void setup() {
		size(1024, 768, P2D);
		setPAppletSingleton();

		textureMode(NORMAL);

		lights = new LightsManager();
		lights.setup();

		clipManager = new ClipManager(lights);

		createControllers();

	}

	public void draw() {
		frame.setTitle(Integer.toString(((int) frameRate)) + " fps");
		background(25, 25, 50);
		drawBackLines();

		clipManager.update();
		clipManager.render();
		lights.update();
		// lights.drawCalibration();
	}

	private void drawBackLines() {
		stroke(200);
		float offset = frameCount % 40;
		for (int i = 0; i < width; i += 40) {
			line(i + offset, 0, i + offset, height);
		}

		// MOUSE POSITION
		fill(255);
		text("FR: " + frameRate, 20, 20);
		text("X: " + mouseX + " / Y: " + mouseY, mouseX, mouseY);

	}

	public void keyPressed() {

		clipManager.onKeyPressed(key);
		lights.onKeyPressed(key);

		if (key == 'e') {
			
			clipManager.toggleEditMode();
			
			lights.toggleEditMode();
			
			if (lights.editMode) {
				cp5.show();
			} else {
				cp5.hide();
			}
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
		lights.onMouseDragged();
	}

	public void mouseMoved() {
		// ship.onMouseMoved();
	}

	// GUI - BEGIN -------------------------------------------------------------------------

	private void createControllers() {

		cp5 = new ControlP5(this);

		cp5.addSlider("Ring_Count").setPosition(20, 610).setSize(150, 20).setRange(2, 20).setNumberOfTickMarks(19).setValue(5).snapToTickMarks(true).showTickMarks(true);
		cp5.addSlider("Ray_Count").setPosition(20, 650).setSize(150, 20).setRange(3, 16).setNumberOfTickMarks(14).setValue(8).snapToTickMarks(true).showTickMarks(true);
		cp5.addButton("Reset_Lights").setPosition(20, 690).setSize(70, 30).setColor(new CColor(color(0,200,200),color(0,150,150),color(0,200,200),color(255),color(255)));
		
		cp5.hide();
		
		}

	public void Ring_Count(float count) {
		lights.modifyRingCount((int) count);
	}
	public void Ray_Count(float count) {
		lights.modifyRayCount((int) count);
	}
	
	public void Reset_Lights(){
		lights.resetLightSetup();
		
		cp5.getController("Ring_Count").setValue(5);
		cp5.getController("Ray_Count").setValue(8);

	}

	// GUI - END ---------------------------------------------------------------------------

	// *******************************************
	// *******************************************

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
