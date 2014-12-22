package globals;

public class Clip {

	protected Main p5;
	boolean isPlaying;

	public Clip() {
		p5 = getP5();
	}

	public void load() {
		isPlaying = false;
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

	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}