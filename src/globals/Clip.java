package globals;

import Lights.LightsManager;
import processing.core.PGraphics;
import processing.core.PVector;

public class Clip {

	protected Main p5;
	boolean isPlaying;
	public boolean useProjectionForLights;

	String name;
	// public static PVector center;

	protected PGraphics projection;
	protected PGraphics lights;

	protected boolean[] triggers;

	public Clip() {
		p5 = getP5();
	}

	public void load() {
		isPlaying = false;
		useProjectionForLights = false;

		name = "??";

		//int layerBoxSize = p5.height;
		int layerBoxSize = 600; // RESOLUCION Q SE LA BANCA
		
		projection = p5.createGraphics(layerBoxSize, layerBoxSize); // SI PONGO
		
																	// P2D, EL
																	// FRAMERATE
																	// DROPPEA
																	// MAAAAALL..!!
		lights = p5.createGraphics(layerBoxSize, layerBoxSize);

		triggers = new boolean[5];
		for (int i = 0; i < triggers.length; i++) {
			triggers[i] = false;
		}

		// center = LightsManager.center;
	}

	public void start() {
		isPlaying = true;
	}

	public void stop() {
		isPlaying = false;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setName(String _name) {
		name = _name;
	}

	public void trigger(int triggerId) {
		triggers[triggerId] = true;
	}

	public void resetTriggers() {
		for (int i=0; i < triggers.length; i++) {
			triggers[i] = false;
		}
	}

	public String getName() {
		return name;
	}

	public void useProjectionForLights(boolean state) {
		useProjectionForLights = state;
	}

	public void updateProjection() {
	}

	public void updateLights() {
	}

	public void renderProjection() {
	}

	public void renderLights() {
	}

	public PGraphics getProjectionLayer() {
		return projection;
	}

	public PGraphics getLightsLayer() {
		if (useProjectionForLights) {
			return projection;
		} else {
			return lights;
		}
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}