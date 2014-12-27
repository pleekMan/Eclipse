package Lights;

import java.util.Arrays;

import processing.core.PGraphics;
import processing.core.PVector;
import globals.Main;
import globals.PAppletSingleton;

public class LightsManager {

	Main p5;

	int ledsRayCount;
	int ledsPerStrip;

	PVector center;
	PVector[] pickers;

	float innerRadius, outerRadius, offset;
	float editDragDistance;

	public boolean editMode;

	PGraphics lightLayer;

	int[] pickerColors;
	int centralColor;

	public LightsManager() {
		p5 = getP5();
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
		for (int i = 0; i < pickers.length; i++) {
			pickerColors[i] = lightLayer.get((int) pickers[i].x, (int) pickers[i].y);
		}
		centralColor = lightLayer.get((int) center.x, (int) center.y);
	}

	public void drawCalibration() {

		// CENTER PICKER (ECLIPSE)
		p5.noFill();
		p5.stroke(200, 0, 200);
		p5.ellipse(center.x, center.y, innerRadius * 2, innerRadius * 2);
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

	public void addRing() {
		modifyPickersCount(0, 1);
	}

	public void removeRing() {
		modifyPickersCount(0, -1);
	}

	public void addRay() {
		modifyPickersCount(1, 0);
	}

	public void removeRay() {
		modifyPickersCount(-1, 0);
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

	// USED WITH ADD AND REMOVE METHODS
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
	
	public void resetLightSetup(){
		
		ledsRayCount = 8;
		ledsPerStrip = 5;

		pickers = new PVector[ledsRayCount * ledsPerStrip];
		pickerColors = new int[pickers.length];
		
		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i / (float) pickers.length) * 255f, 0, 0);
		}
		
		center = new PVector(p5.width * 0.5f, p5.height * 0.5f);
		innerRadius = 100;
		outerRadius = 350;
		offset = 0;
		
		setupPickers();
	}


	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
