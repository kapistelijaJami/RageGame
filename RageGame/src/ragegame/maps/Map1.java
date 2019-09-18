package ragegame.maps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.ObjectHandler;
import ragegame.RageGame;
import ragegame.Rect;
import ragegame.TrapHandler;
import ragegame.Wall;
import ragegame.traps.Spikes;

public class Map1 extends Map {
	private TrapHandler trapHandler;
	private Rect goal;
	private int playerStartX;
	private int playerStartY;
	
	public Map1(RageGame peli) {
		this.peli = peli;
		
		init();
	}
	
	public void init() {
		this.objectHandler = new ObjectHandler();
		this.trapHandler = new TrapHandler();
		
		//Platforms
		this.objectHandler.add(new Wall(50, 500, 900, 50));
		this.objectHandler.add(new Wall(900, 400, 50, 30));
		this.objectHandler.add(new Wall(850, 250, 50, 30));
		
		//Walls
		this.objectHandler.add(new Wall(500, 50, 150, 437));
		this.objectHandler.add(new Wall(800, 50, 50, 350));
		this.objectHandler.add(new Wall(950, 50, 50, 380));
		
		
		this.goal = new Rect(900, 100, 50, 50);
		
		playerStartX = 100;
		playerStartY = 100;
		
		Spikes spikes = new Spikes(650, 500, 20, 20, 0, peli);
		spikes.setTrigger(new Rect(600, 480, 20, 20));
		this.trapHandler.add(spikes);
	}

	@Override
	public void update() {
		this.trapHandler.update();
		if (peli.intersectsWithPlayer(goal)) {
			peli.win();
		}
	}
	
	@Override
	public void render(Graphics g) {
		this.objectHandler.render(g);
		this.trapHandler.render(g);
		this.goal.renderRect(g, Color.GREEN);
	}

	@Override
	public int getPlayerStartX() {
		return playerStartX;
	}

	@Override
	public int getPlayerStartY() {
		return playerStartY;
	}

	@Override
	public void reset() {
		init();
	}
}
