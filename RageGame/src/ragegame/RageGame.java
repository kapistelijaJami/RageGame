package ragegame;

import java.awt.Canvas;
import javax.swing.JFrame;

public class RageGame extends Canvas implements Runnable {
	
	public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final double updatesPerSecond = 60.0;
	
	public boolean running = false;
    public Thread thread;
	public static JFrame frame;
	
	public void createWindow() {
        Window window = new Window(WIDTH, HEIGHT, "RageGame", this);
        frame = window.getFrame();
    }
	
	public synchronized void start() {
		if (running) {
            return;
        }
		
		thread = new Thread(this);
        thread.start();
	}
	
	@Override
	public void run() {
		
	}
}
