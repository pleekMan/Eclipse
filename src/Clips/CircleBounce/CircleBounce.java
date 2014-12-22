package Clips.CircleBounce;

import globals.Clip;
import globals.Main;
import globals.PAppletSingleton;

public class CircleBounce extends Clip {

	float diameter;
	float velocity;
	boolean isPlaying;

	public CircleBounce(){
		super();
	}
	
	@Override
	public void load() {
		
		super.load();
		
		diameter = 1;
		velocity = 2;
		isPlaying = false;
	}
	
	@Override
	public void update() {
		diameter += velocity;
		if(diameter > p5.height || diameter < 0) velocity *= -1;
	}

	@Override
	public void render() {
		p5.background(255);
		p5.noFill();
		p5.stroke(0);
		p5.ellipse(p5.width * 0.5f, p5.height * 0.5f, diameter, diameter);

	}


}
