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
import ragegame.traps.SpikesDiagonal;
import ragegame.traps.Trampoline;

public class Map5 extends Map {
	private TrapHandler trapHandler;
	private Rect goal;
	private int playerStartX;
	private int playerStartY;
	
	public Map5(RageGame peli) {
		this.peli = peli;
		
		init();
	}
	
	public void init() {
		this.objectHandler = new ObjectHandler();
		this.trapHandler = new TrapHandler();
		
		//Platforms
		this.objectHandler.add(new DiagonalWall(100, 400, 500, 30, -45));
		this.objectHandler.add(new DiagonalWall(100, 250, 500, 30, -45));
		
		this.objectHandler.add(new Wall(70, 600, 50, 30));
		
		
		//Walls
		this.objectHandler.add(new Wall(150, 40, 30, 400));
		
		
		this.trapHandler.add(new SpikesDiagonal(106, 267, 500, 10, 2, -45, true, peli));
		
		this.goal = new Rect(700, 100, 50, 50);
		
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
