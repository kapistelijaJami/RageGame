package ragegame.traps;

import java.awt.Graphics;
import ragegame.RageGame;
import ragegame.Rect;

public abstract class Trap extends Rect {
	protected boolean armed;
	protected RageGame peli;
	
	public Trap(int x, int y, int width, int height, boolean armed, RageGame peli) {
		super(x, y, width, height);
		this.armed = armed;
		this.peli = peli;
	}
	
	public abstract void armTrap();
	public abstract void fire();

	public abstract void render(Graphics g);
	public abstract void update();
}
