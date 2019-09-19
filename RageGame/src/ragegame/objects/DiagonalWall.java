package ragegame.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import ragegame.RageGame;
import ragegame.Rect;

public class DiagonalWall extends Wall {
	protected double kulma;

	public DiagonalWall(int x, int y, int width, int height, double kulma) {
		super(x, y, width, height);
		
		this.kulma = kulma;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(kulma), x + width / 2, y + height / 2);
		g2d.fillRect(x, y, width, height);
		
		g2d.setTransform(old);
	}
	
	@Override
	public boolean intersects(Rect comp) {
		Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(kulma), x + width / 2, y + height / 2);
		Shape rotatedRect = at.createTransformedShape(rect);
		
		rect = new Rectangle2D.Double(comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight());
		return RageGame.shapeIntersects(rotatedRect, rect);
	}
	
	public boolean intersects(Rect comp1, Rect comp2) {
		return this.intersects(comp1) && this.intersects(comp2);
	}
}
