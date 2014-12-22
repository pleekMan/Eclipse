package Clips.Hedera;
import globals.Main;
import globals.PAppletSingleton;

public class Timer {
	
	Main p5;
	int savedTime;

	int totalTime;

	int currentTime;

	Timer() {
		p5 = getP5();
		totalTime = 0;
	}

	public void setDuration(int tempTotalTime) {// tiempo

		totalTime = tempTotalTime;
	}

	public void start() {// inicia

		savedTime = p5.millis();
	}

	public boolean isFinished() {// sitermino o no

		currentTime = p5.millis() - savedTime;

		if (currentTime > totalTime) {

			return true;
		} else {

			return false;
		}
	}

	public int getTotalTime() {// tiempo actual

		return totalTime;
	}

	public int getCurrentTime() {

		return currentTime;
	}
	
	protected Main getP5() {
		return PAppletSingleton.getInstance().getP5Applet();
	}
}
