package ragegame.maps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.ObjectHandler;
import ragegame.RageGame;
import ragegame.Rect;
import ragegame.TrapHandler;
import ragegame.objects.DiagonalWall;
import ragegame.objects.MovingWall;
import ragegame.objects.Wall;
import ragegame.traps.Spikes;
import ragegame.traps.Trampoline;

public class Map4 extends Map {
	private TrapHandler trapHandler;
	private Rect goal;
	private int playerStartX;
	private int playerStartY;
	
	public Map4(RageGame peli) {
		this.peli = peli;
		
		init();
	}
	
	public void init() {
		this.objectHandler = new ObjectHandler();
		this.trapHandler = new TrapHandler();
		
		//Platforms
		this.objectHandler.add(new Wall(50, 500, 100, 30));
		this.objectHandler.add(new Wall(850, 500, 100, 30));
		this.objectHandler.add(new Wall(450, 200, 550, 30));
		
		this.objectHandler.add(new Wall(350, 300, 150, 30));
		
		double maxSpeed = 7;
		this.objectHandler.add(new MovingWall(200, 500, 100, 30, 270, 620, maxSpeed, 0, peli));
		
		//Walls
		this.objectHandler.add(new Wall(200, 40, 30, 100));
		this.objectHandler.add(new Wall(950, 200, 30, 330));
		
		
		this.trapHandler.add(new Trampoline(850, 500, 100, 5, 0, 10, true, peli));
		this.trapHandler.add(new Trampoline(225, 40, 5, 100, 1, 23, true, peli));
		
		
		//this.objectHandler.add(new DiagonalWall(100, 400, 100, 30, 45));
		
		this.trapHandler.add(new Spikes(450, 200, 400, 10, 0, true, peli));
		
		this.goal = new Rect(900, 100, 50, 50);
		
		playerStartX = 90;
		playerStartY = 400;
		
	}

	@Override
	public void update() {
		this.objectHandler.update();
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
