package Lights;

import controlP5.Println;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.data.XML;
import processing.serial.*;
import globals.Main;
import globals.PAppletSingleton;

public class LightsManager {

	Main p5;
	Serial serialPort;

	int ledsRayCount;
	int ledsPerStrip;

	PVector[] pickers;

	public static PVector center;
	public static float innerRadius, outerRadius, offset;
	float editDragDistance;

	public boolean editMode;

	PGraphics lightLayer;

	int[] pickerColors;
	int centralColor;

	public LightsManager() {
		p5 = getP5();

		p5.println(Serial.list());
		try {
			String portName = Serial.list()[5]; // ARDUINO IS USUALLY PORT 5
												// (tty.usbmodemXXXX)
			serialPort = new Serial(p5, portName, 115200);
		} catch (Exception e) {
			p5.println("*********************");
			p5.println("NO ENCHUFASTE EL ARDUINO, GILUN");
			p5.println("*********************");

			// e.printStackTrace();
		}
	}

	public void setup() {

		resetLightSetup();
		setupPickers();

		centralColor = p5.color(0);

		bindToLightLayer(null);

		editMode = false;
		editDragDistance = 20f;

	}

	private void setupPickers() {

		// CALCULATE RADII OF LEDS IN ALL STRIPS
		float[] radii = new float[ledsPerStrip];
		for (int i = 0; i < radii.length; i++) {
			radii[i] = p5.map(i + 1, 0, ledsPerStrip, innerRadius, outerRadius) + offset;
		}

		float stripsAngle = p5.TWO_PI / ledsRayCount;
		for (int i = 0; i < ledsRayCount; i++) {

			float rotation = (stripsAngle * i) - p5.HALF_PI;

			for (int j = 0; j < ledsPerStrip; j++) {
				int actualPicker = (i * ledsPerStrip) + j;
				pickers[actualPicker].x = center.x + (radii[j] * p5.cos(rotation));
				pickers[actualPicker].y = center.y + (radii[j] * p5.sin(rotation));

			}
		}

	}

	public void update() {
		if (lightLayer != null) {
			pick();
		}

		if (editMode) {
			drawCalibration();
		}
	}

	private void pick() {

		float negativeOuterRadiusX = center.x - outerRadius - offset;
		float positiveOuterRadiusX = center.x + outerRadius + offset;
		float negativeOuterRadiusY = center.y - outerRadius - offset;
		float positiveOuterRadiusY = center.y + outerRadius + offset;

		for (int i = 0; i < pickers.length; i++) {

			// int pickX = (int)((pickers[i].x / p5.width) * lightLayer.width);

			// IF THE CLIPS ARE GOING TO BE MOVING ALONG THE CALIBRATION, THE
			// PICKERS NEED TO ADJUST THEIR (global X Y ) PICKING
			// TO THE CLIPS SIZE/TRANSFORM PICK
			int pickX = (int) (p5.map(pickers[i].x, negativeOuterRadiusX, positiveOuterRadiusX, 0, lightLayer.width));
			int pickY = (int) (p5.map(pickers[i].y, negativeOuterRadiusY, positiveOuterRadiusY, 0, lightLayer.height));

			pickerColors[i] = lightLayer.get(pickX, pickY);
			// pickerColors[i] = lightLayer.get((int) pickers[i].x, (int)
			// pickers[i].y);
		}
		centralColor = lightLayer.get((int) (lightLayer.width * 0.5f), (int) (lightLayer.height * 0.5f));

		// SEND COLORS
		sendColors();
	}

	private void sendColors() {

		//if (p5.frameCount % 4 == 0) { // SENDING AT EVERY FRAME DROPS FRAMERATE TO 18
			
			if (serialPort != null) {

				//int realLEDCount = 120;
				//for (int i = 0; i < realLEDCount; i++) {
				
				for (int i = 0; i < pickers.length; i++) {
					byte[] toSend = { (byte) (p5.red(pickerColors[i])), (byte) (p5.green(pickerColors[i])), (byte) (p5.blue(pickerColors[i])) };
					serialPort.write(toSend);
				}
			}
		//}
	}
	
	@Deprecated
	private void sendColorsArray() {
		
		// TRYING TO SEND THE COLORS ONLY ONCE.... IT'S THE SAME..
		
		//if (p5.frameCount % 4 == 0) { // SENDING AT EVERY FRAME DROPS FRAMERATE TO 18
			
		byte[] colorArray = new byte[pickers.length * 3];
		
			if (serialPort != null) {

				//int realLEDCount = 120;
				//for (int i = 0; i < realLEDCount; i++) {
				
				for (int i = 0; i < pickers.length; i++) {
					byte[] toSend = { (byte) (p5.red(pickerColors[i])), (byte) (p5.green(pickerColors[i])), (byte) (p5.blue(pickerColors[i])) };
					
					for (int j = 0; j < toSend.length; j++) {
						colorArray[i+j] = toSend[j];
					}
					//serialPort.write(toSend);
				}
			}
			
			serialPort.write(colorArray);
		//}
	}

	public void drawCalibration() {

		// CENTER PICKER (ECLIPSE)
		p5.noFill();
		p5.stroke(200, 0, 200);
		p5.ellipse(center.x, center.y, innerRadius * 2, innerRadius * 2);
		p5.ellipse(center.x, center.y, outerRadius * 2, outerRadius * 2);

		p5.noStroke();
		p5.fill(0);
		p5.ellipse(center.x, center.y, 15, 15);
		p5.fill(centralColor);
		p5.ellipse(center.x, center.y, 10, 10);

		// PICKERS
		p5.noStroke();
		for (int i = 0; i < pickers.length; i++) {

			p5.fill(0);
			p5.ellipse(pickers[i].x, pickers[i].y, 15, 15);
			p5.fill(pickerColors[i]);
			p5.ellipse(pickers[i].x, pickers[i].y, 10, 10);

			p5.fill(255, 50, 50);
			p5.text(i, pickers[i].x + 10, pickers[i].y);
		}

		// FLOATING HELPERS
		float mouseFromCenter = p5.dist(p5.mouseX, p5.mouseY, center.x, center.y);

		p5.noFill();
		p5.stroke(0, 255, 0);
		// OUTER RADIUS
		if (mouseFromCenter > (outerRadius - editDragDistance) && mouseFromCenter < (outerRadius + editDragDistance)) {
			p5.ellipse(p5.mouseX, p5.mouseY, editDragDistance, editDragDistance);
			p5.line(center.x, center.y, p5.mouseX, p5.mouseY);
			p5.ellipse(center.x, center.y, outerRadius * 2, outerRadius * 2);
		} else
		// INNER RADIUS
		if (mouseFromCenter > (innerRadius - editDragDistance) && mouseFromCenter < (innerRadius + editDragDistance)) {
			p5.ellipse(p5.mouseX, p5.mouseY, editDragDistance, editDragDistance);
			p5.line(center.x, center.y, p5.mouseX, p5.mouseY);
			p5.ellipse(center.x, center.y, innerRadius * 2, innerRadius * 2);

		} else
		// CENTER
		if (mouseFromCenter < editDragDistance) {
			p5.ellipse(p5.mouseX, p5.mouseY, editDragDistance, editDragDistance);
			// p5.ellipse(center.x, center.y, innerRadius * 2, innerRadius * 2);
		}

	}

	public void bindToLightLayer(PGraphics _lightLayer) {
		lightLayer = _lightLayer;
	}

	public void toggleEditMode() {
		editMode = !editMode;
	}

	public void onKeyPressed(char key) {

		if (key == 't') {
			addRay();
		}
		if (key == 'h') {
			addRing();
		}
		if (key == 'g') {
			removeRay();
		}
		if (key == 'f') {
			removeRing();
		}

		/*
		 * // SELECT AND LOAD CLIPS switch (key) { case '1': CircleBounce
		 * circleBounce = new CircleBounce(); circleBounce.load();
		 * clips.add(circleBounce); System.out.println("Loaded :: " +
		 * CircleBounce.class.getName()); break; default:
		 * //System.out.println("No Clip Found at: " + selectedClip); break; }
		 */

	}

	public void onMouseDragged() {

		if (editMode) {
			modifyRadii();
		}

	}

	private void modifyRadii() {

		float mouseFromCenter = p5.dist(p5.mouseX, p5.mouseY, center.x, center.y);

		// OUTER RADIUS
		if (mouseFromCenter > (outerRadius - editDragDistance) && mouseFromCenter < (outerRadius + editDragDistance)) {
			outerRadius = mouseFromCenter;
			setupPickers();
		} else
		// INNER RADIUS
		if (mouseFromCenter > (innerRadius - editDragDistance) && mouseFromCenter < (innerRadius + editDragDistance)) {
			innerRadius = mouseFromCenter;
			setupPickers();
		} else
		// CENTER
		if (mouseFromCenter < editDragDistance) {
			center.set(p5.mouseX, p5.mouseY);
			setupPickers();
		}

	}

	@Deprecated
	public void addRing() {
		modifyPickersCount(0, 1);
	}

	@Deprecated
	public void removeRing() {
		modifyPickersCount(0, -1);
	}

	@Deprecated
	public void addRay() {
		modifyPickersCount(1, 0);
	}

	@Deprecated
	public void removeRay() {
		modifyPickersCount(-1, 0);
	}

	// USED WITH ADD AND REMOVE METHODS
	@Deprecated
	private void modifyPickersCount(int addRay, int addRing) {

		ledsPerStrip += addRing;
		ledsRayCount += addRay;

		pickers = (PVector[]) p5.expand(pickers, ledsRayCount * ledsPerStrip);
		pickerColors = p5.expand(pickerColors, pickers.length);
		pickerColors = new int[pickers.length];
		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}

		setupPickers();
	}

	// USED WITH GUI SLIDERS
	public void modifyRingCount(int count) {

		ledsPerStrip = count;

		pickers = (PVector[]) p5.expand(pickers, ledsRayCount * ledsPerStrip);
		pickerColors = p5.expand(pickerColors, pickers.length);
		pickerColors = new int[pickers.length];
		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}

		setupPickers();
	}

	// USED WITH GUI SLIDERS
	public void modifyRayCount(int count) {

		ledsRayCount = count;

		pickers = (PVector[]) p5.expand(pickers, ledsRayCount * ledsPerStrip);
		pickerColors = p5.expand(pickerColors, pickers.length);
		pickerColors = new int[pickers.length];
		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}

		setupPickers();
	}

	public void setPickerOffset(float _offset) {
		offset = _offset;
		setupPickers();
	}

	public void resetLightSetup() {

		ledsRayCount = 8;
		ledsPerStrip = 15;

		center = new PVector(p5.width * 0.5f, p5.height * 0.5f);
		innerRadius = 100;
		outerRadius = 350;
		offset = 0;

		// /////////////

		pickers = new PVector[ledsRayCount * ledsPerStrip];
		pickerColors = new int[pickers.length];

		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}

		setupPickers();
	}

	public void loadLightSettings() {

		XML settingsFile = p5.loadXML("settings.xml"); // reads and stand at the
														// Master Node
		XML lightSettings = settingsFile.getChild("lights");

		center = new PVector(lightSettings.getFloat("centerX"), lightSettings.getFloat("centerY"));
		innerRadius = lightSettings.getFloat("innerRadius");
		outerRadius = lightSettings.getFloat("outerRadius");
		offset = lightSettings.getFloat("offset");

		ledsRayCount = lightSettings.getInt("rays");
		ledsPerStrip = lightSettings.getInt("rings");

		// /////////////

		pickers = new PVector[ledsRayCount * ledsPerStrip];
		pickerColors = new int[pickers.length];

		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}

		setupPickers();

	}

	public void saveLightSettings() {

		XML settingsFile = new XML("settings");
		XML lightSettings = settingsFile.addChild("lights");

		lightSettings.setFloat("centerX", center.x);
		lightSettings.setFloat("centerY", center.y);
		lightSettings.setFloat("innerRadius", innerRadius);
		lightSettings.setFloat("outerRadius", outerRadius);
		lightSettings.setFloat("offset", offset);

		lightSettings.setInt("rays", ledsRayCount);
		lightSettings.setInt("rings", ledsPerStrip);

		p5.saveXML(settingsFile, "bin/data/settings.xml");
	}

	public PVector getCenter() {
		return center;
	}

	public static float getBoundingBoxDimension() {
		return (outerRadius * 2) + (offset * 2);
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
