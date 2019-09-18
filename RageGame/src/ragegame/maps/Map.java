package ragegame.maps;

import java.awt.Graphics;
import ragegame.ObjectHandler;
import ragegame.RageGame;

public abstract class Map {
	protected ObjectHandler objectHandler;
	protected RageGame peli;
	
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract int getPlayerStartX();
	public abstract int getPlayerStartY();
	
	public ObjectHandler getObjectHandler() {
		return objectHandler;
	}

	public abstract void reset();
}
