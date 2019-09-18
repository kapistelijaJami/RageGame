package ragegame.maps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.ObjectHandler;
import ragegame.RageGame;
import ragegame.Rect;
import ragegame.TrapHandler;
import ragegame.Wall;
import ragegame.traps.Spikes;

public class Map2 extends Map {
	private TrapHandler trapHandler;
	private Rect goal;
	private int playerStartX;
	private int playerStartY;
	
	public Map2(RageGame peli) {
		this.peli = peli;
		
		init();
	}
	
	public void init() {
		this.objectHandler = new ObjectHandler();
		this.trapHandler = new TrapHandler();
		
		//Platforms
		this.objectHandler.add(new Wall(52, 500, 16, 20));
		this.objectHandler.add(new Wall(200, 460, 16, 20));
		this.objectHandler.add(new Wall(400, 550, 16, 20));
		this.objectHandler.add(new Wall(550, 400, 16, 20));
		this.objectHandler.add(new Wall(350, 300, 16, 20));
		this.objectHandler.add(new Wall(600, 200, 16, 20));
		
		
		//Walls
		this.objectHandler.add(new Wall(300, 50, 50, 437));
		this.objectHandler.add(new Wall(800, 150, 40, 437));
		
		
		this.goal = new Rect(900, 500, 50, 50);
		
		playerStartX = 50;
		playerStartY = 300;
		
		
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
