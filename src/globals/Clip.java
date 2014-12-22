package globals;

import processing.core.PGraphics;

public class Clip {

	protected Main p5;
	boolean isPlaying;
	
	protected PGraphics projection;
	protected PGraphics lights;

	public Clip() {
		p5 = getP5();
	}

	public void load() {
		isPlaying = false;
		
		projection = p5.createGraphics(p5.width, p5.height);
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
	
	public void update() {

	}

	public void render() {

	}
	
	public PGraphics getProjectionLayer(){
		return projection;
	}
	public PGraphics getLightsLayer(){
		return lights;
	}

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}