package Lights;

import Clips.CircleBounce.CircleBounce;
import Clips.Hedera.Hedera;
import Clips.LineColor.LineColor;
import Clips.RectBounce.RectBounce;
import processing.core.PGraphics;
import processing.core.PVector;
import globals.Main;
import globals.PAppletSingleton;

public class LightsManager {

	Main p5;
	
	int ledStripsCount;
	int ledsPerStrip;
	
	PVector center;
	PVector[] pickers;
	
	float innerRadius, outerRadius, offset;
	
	boolean editMode;
	
	PGraphics lightLayer;
	
	int[] pickerColors;
	int centralColor;
	
	public LightsManager(){
		p5 = getP5();
	}
	
	public void setup(){
		
		ledStripsCount = 8;
		ledsPerStrip = 5;
		
		center = new PVector(p5.width * 0.5f, p5.height * 0.5f);
		innerRadius = 100;
		outerRadius = 350;
		offset = 50;
				
		pickers = new PVector[ledStripsCount * ledsPerStrip];
		pickerColors = new int[pickers.length];
		for (int i = 0; i < pickers.length; i++) {
			pickers[i] = new PVector();
			pickerColors[i] = p5.color((i/(float)pickers.length) * 255f,0,0);
		}
		centralColor = p5.color(0);
		
		bindToLightLayer(null);
		
		editMode = false;

		setupPickers();
		
	}
	
	private void setupPickers() {
		
		// CALCULATE RADII OF LEDS IN ALL STRIPS
		float[] radii = new float[ledsPerStrip];
		for (int i = 0; i < radii.length; i++) {
			radii[i] = p5.map(i, 0, ledsPerStrip, innerRadius, outerRadius) + offset;
		}
		
		float stripsAngle = p5.TWO_PI / ledStripsCount;
		for (int i = 0; i < ledStripsCount; i++) {
			
			float rotation = (stripsAngle * i) - p5.HALF_PI;
			
			for (int j = 0; j < ledsPerStrip; j++) {
				int actualPicker = (i * ledsPerStrip) + j;
				pickers[actualPicker].x = center.x + (radii[j] * p5.cos(rotation));
				pickers[actualPicker].y = center.y + (radii[j] * p5.sin(rotation));
				
			}
		}
		
	}
	
	public void update(){
		if (lightLayer != null) {
			pick();
		}
		
		if (editMode) {
			drawCalibration();
		}
	}
	
	private void pick() {
		for (int i = 0; i < pickers.length; i++) {
			pickerColors[i] = lightLayer.get((int)pickers[i].x, (int)pickers[i].y);
		}
		centralColor = lightLayer.get((int)center.x, (int)center.y);
	}

	public void drawCalibration(){
		
		
		// CENTER PICKER (ECLIPSE)
		p5.noFill();
		p5.stroke(200,0,200);
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
			
			p5.fill(255,50,50);
			p5.text(i, pickers[i].x + 10, pickers[i].y);
		}
	}
	
	public void bindToLightLayer(PGraphics _lightLayer){
		lightLayer = _lightLayer;
	}
	
	public void toggleEditMode(){
		editMode = !editMode;
	}
	
public void onKeyPressed(char key) {
		
	/*
	if(key == 'e'){
		toggleEditMode();
	}
	*/
	
	/*
		// SELECT AND LOAD CLIPS
		switch (key) {
		case '1':
			CircleBounce circleBounce = new CircleBounce();
			circleBounce.load();
			clips.add(circleBounce);
			System.out.println("Loaded :: " + CircleBounce.class.getName());
			break;
		default:
			//System.out.println("No Clip Found at: " + selectedClip);
			break;
		}
		*/
		
}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
