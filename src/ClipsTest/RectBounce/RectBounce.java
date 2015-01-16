package ClipsTest.RectBounce;

import globals.Clip;
import globals.Main;
import globals.PAppletSingleton;

public class RectBounce extends Clip {

	float diameter;
	float velocity;
	boolean isPlaying;

	public RectBounce(){
		super();
	}
	
	@Override
	public void load() {
		super.load();
		
		diameter = 1;
		velocity = 2;
		isPlaying = false;
		
		p5.rectMode(p5.CENTER);
	}
	
	@Override
	public void updateProjection() {
		
	}

	@Override
	public void renderProjection() {
		
		diameter += velocity;
		if(diameter > p5.height || diameter < 0) velocity *= -1;
		
		p5.background(255);
		p5.noFill();
		p5.stroke(0);
		p5.rect(p5.width * 0.5f, p5.height * 0.5f, diameter, diameter);

	}


}
