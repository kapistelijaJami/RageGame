package ragegame;

import java.awt.Color;
import java.awt.Graphics;

public class Wall extends Rect{
	
	public Wall(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
}
