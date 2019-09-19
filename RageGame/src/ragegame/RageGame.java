package ragegame;

import ragegame.objects.Wall;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import ragegame.maps.Map;
import ragegame.maps.Map1;
import ragegame.maps.Map2;
import ragegame.maps.Map3;
import ragegame.maps.Map4;
import ragegame.maps.Map5;
import ragegame.maps.Map6;

public class RageGame extends Canvas implements Runnable {
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final double updatesPerSecond = 60.0;
	
	public int counter = 0;
	
	public boolean running = false;
	public Thread thread;
	public static JFrame frame;
	
	private Player player;
	private KeyInput input;
	
	private ArrayList<Map> mapList;
	private Map currentMap;
	
	private int deaths = 0;
	private boolean killPlayer = false;
	private int killPlayerCounter = 0;
	
	public void createWindow() {
		Window window = new Window(WIDTH, HEIGHT, "RageGame", this);
		frame = window.getFrame();
    }
	
	public synchronized void start() {
		if (running) {
			return;
		}
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		try {
			running = false;
			frame.dispose();
			System.exit(0);
			thread.join();
		} catch (Exception e) {
		}
	}
	
	public void init() {	//Tee leveli ja pelaaja
		player = new Player(0, 0, 20, 25, this);
		mapList = new ArrayList<>();
		
		mapList.add(new Map1(this));
		mapList.add(new Map2(this));
		mapList.add(new Map3(this));
		mapList.add(new Map4(this));
		mapList.add(new Map5(this));
		mapList.add(new Map6(this));
		
		currentMap = mapList.get(0); //starting map
		player.setX(currentMap.getPlayerStartX());
		player.setY(currentMap.getPlayerStartY());
		
		input = new KeyInput(this.player, this);
        this.addKeyListener(input);
	}
	
	@Override
	public void run() {
		init();
		this.requestFocus();
		
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double nanoSecondsPerUpdate = 1000000000 / updatesPerSecond;	//päivitysten väliin tarvittavat nanosekunnit
		double delta = 0;												//nanosekuntien määrä suhteessa päivitykseen tarvittaviin nanosekunteihin, kun 1, niin tehdään päivitys
		
		int updates = 0;		//päivitysten määrä sekunnissa
		int frames = 0;			//renderöintien määrä sekunnissa
		
		while (running) {
			/*update();
			updates++;
			render();
			frames++;*/
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSecondsPerUpdate;		//deltaan lisätään loopiin kuluneet nanosekunnit per päivitykseen tarvittavat nanosekunnit
			lastTime = now;
			
			while (delta >= 1) {	//deltan arvo on 1 kun on mennyt 1/updatesPerSecond sekuntia (eli 1/60)
				update();
				updates++;
				render();
				frames++;
				delta--;			//deltan arvoa miinustetaan yhdellä (eli putoaa hyvin lähelle lukua 0)
			}
			
			if (System.currentTimeMillis() - timer > 1000) {		//jos on mennyt sekunti viime kerrasta (tänne pääsee siis sekunnin välein)
				timer += 1000;		//timer jahtaa currentTimeMillis funktiota
				frame.setTitle("RageGame " + "Updates: " + updates + ", Frames: " + frames);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void update() {
		player.update();
		render();
		currentMap.update();
		
		if (killPlayer && killPlayerCounter > 10) {
			killPlayerCounter = 0;
			killPlayer = false;
			restartLevel();
		} else if (killPlayer) {
			killPlayerCounter++;
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT); //valkonen tausta
		
		player.render(g);
		currentMap.render(g);
		
		g.setColor(Color.MAGENTA);
		Graphics2D g2d = (Graphics2D) g;
		
		
		/*g2d.rotate(Math.toRadians(-counter), 150, 150);
		g2d.fillRect(100, 100, 100, 100);
		counter += 10;*/
		
		g.dispose();
        bs.show();
	}

	public Wall intersectsWithWall(Rect comp) {
		for (Wall wall : currentMap.getObjectHandler().getList()) {
			if (wall.intersects(comp)) {
				return wall;
			}
		}
		
		return null;
	}
	
	public Wall intersectsWithWall(Rect comp, Rect comp2) {
		for (Wall wall : currentMap.getObjectHandler().getList()) {
			if (wall.intersects(comp) && wall.intersects(comp2)) {
				return wall;
			}
		}
		
		return null;
	}
	
	public boolean intersectsWithPlayer(Rect comp) {
		return player.intersects(comp);
	}

	public void killPlayer() {
		player.kill();
		deaths++;
		killPlayer = true;
		System.out.println(deaths);
	}

	public void win() {
		nextMap();
	}
	
	public void nextMap() {
		for (int i = 0; i < mapList.size(); i++) {
			Map map = mapList.get(i);
			if (map.equals(currentMap)) {
				if (i + 1 < mapList.size()) {
					currentMap = mapList.get(i + 1);
					
					player.reset();
					player.setX(currentMap.getPlayerStartX());
					player.setY(currentMap.getPlayerStartY());
					break;
				} else {
					player.win();
					running = false; //Ei enempää mappeja
				}
			}
		}
	}
	
	public void restartLevel() {
		player.reset();
		player.setX(currentMap.getPlayerStartX());
		player.setY(currentMap.getPlayerStartY());
		
		currentMap.reset();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static boolean shapeIntersects(Shape shapeA, Shape shapeB) {
        Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB));
        return !areaA.isEmpty();
    }
}
