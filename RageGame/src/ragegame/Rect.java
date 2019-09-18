package ragegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Rect {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public Rect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean intersects(Rect comp) {
		Rectangle2D eka = new Rectangle(x, y, width, height);
		return eka.intersects(comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight());
	}
	
	public void renderRect(Graphics g, Color color) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}
