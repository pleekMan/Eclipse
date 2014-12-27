package globals;

import processing.core.PGraphics;

public class Clip {

	protected Main p5;
	boolean isPlaying;
	public boolean useProjectionForLights;
	
	String name;
	
	protected PGraphics projection;
	protected PGraphics lights;

	public Clip() {
		p5 = getP5();
	}

	public void load() {
		isPlaying = false;
		useProjectionForLights = false;
		
		name = "??";
		
		projection = p5.createGraphics(p5.width, p5.height); // SI PONGO P2D, EL FRAMERATE DROPPEA MAAAAALL..!!
		lights = p5.createGraphics(p5.width, p5.height);
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
	
	public void setName(String _name){
		name = _name;
	}
	public String getName(){
		return name;
	}
	
	public void useProjectionForLights(boolean state){
		useProjectionForLights = state;
	}
	
	public void updateProjection() {
	}
	
	public void updateLights(){
	}
	
	public void renderProjection() {
	}
	public void renderLights(){
	}
	
	public PGraphics getProjectionLayer(){
		return projection;
	}
	public PGraphics getLightsLayer(){
		if (useProjectionForLights) {
			return projection;
		} else {
			return lights;
		}	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}