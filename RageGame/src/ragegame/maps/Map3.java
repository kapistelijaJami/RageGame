package ragegame.maps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.ObjectHandler;
import ragegame.RageGame;
import ragegame.Rect;
import ragegame.TrapHandler;
import ragegame.objects.Wall;
import ragegame.traps.Spikes;
import ragegame.traps.Trampoline;

public class Map3 extends Map {
	private TrapHandler trapHandler;
	private Rect goal;
	private int playerStartX;
	private int playerStartY;
	
	public Map3(RageGame peli) {
		this.peli = peli;
		
		init();
	}
	
	public void init() {
		this.objectHandler = new ObjectHandler();
		this.trapHandler = new TrapHandler();
		
		//Platforms
		this.objectHandler.add(new Wall(50, 500, 900, 50));
		
		//Walls
		this.objectHandler.add(new Wall(500, 45, 150, 295));
		this.objectHandler.add(new Wall(500, 360, 150, 150));
		
		//Spikes
		Spikes spikes = new Spikes(500, 300, 20, 20, 3, true, peli);
		spikes.setTriggerTrapRect(new Rect(500 - 5, 300, 20, 20));
		this.trapHandler.add(spikes);
		
		spikes = new Spikes(500, 380, 20, 20, 3, true, peli);
		spikes.setTriggerTrapRect(new Rect(500 - 5, 380, 20, 20));
		this.trapHandler.add(spikes);
		
		this.trapHandler.add(new Trampoline(900, 500, 50, 5, 0, 10, true, peli));
		
		this.goal = new Rect(920, 53, 50, 50);
		
		playerStartX = 100;
		playerStartY = 100;
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
