package ragegame.traps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import ragegame.RageGame;
import ragegame.Rect;
import ragegame.objects.DiagonalWall;

public class SpikesDiagonal extends Trap {
	private double kulma;
	private int direction;
	private Rect armTrapRect;
	private boolean fired = false;

	public SpikesDiagonal(int x, int y, int width, int height, int direction, double kulma, boolean armed, RageGame peli) {
		super(x, y, width, height, armed, peli);
		
		this.kulma = kulma;
		this.direction = direction;
	}

	@Override
	public void armTrap() {
		if (!armed) {
			System.out.println("ARMED AND READY");
		}
		this.armed = true;
	}

	@Override
	public void fire() {
		DiagonalWall wall = getTrapTrigger();
		x = wall.getX();
		y = wall.getY();
		
		fired = true;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		
		g2d.rotate(Math.toRadians(kulma), x + width / 2, y + height / 2);
		g2d.fillRect(x, y, width, height);
		
		g2d.setTransform(old);
		
	}

	@Override
	public void update() {
		if (armTrapRect != null && peli.intersectsWithPlayer(armTrapRect)) {
			armTrap();
		}
		
		if (armed && getTrapTrigger().intersects(peli.getPlayer()) && !fired) {
			fire();
			peli.killPlayer();
		}
	}

	public DiagonalWall getTrapTrigger() {
		DiagonalWall wall = null;
		if (direction == 0) { //0, 1, 2, 3; moves up, right, down, left
			wall = new DiagonalWall(x + (int) (Math.sin(Math.toRadians(kulma)) * 10), y - (int) (Math.cos(Math.toRadians(kulma)) * 10), width, height, kulma);
		} else if (direction == 1) {
			wall = new DiagonalWall(x + (int) (Math.cos(Math.toRadians(kulma)) * 10), y + (int) (Math.sin(Math.toRadians(kulma)) * 10), width, height, kulma);
		} else if (direction == 2) {
			wall = new DiagonalWall(x - (int) (Math.sin(Math.toRadians(kulma)) * 10), y + (int) (Math.cos(Math.toRadians(kulma)) * 10), width, height, kulma);
		} else if (direction == 3) {
			wall = new DiagonalWall(x - (int) (Math.cos(Math.toRadians(kulma)) * 10), y - (int) (Math.sin(Math.toRadians(kulma)) * 10), width, height, kulma);
		}
		
		return wall;
	}
	
	@Override
	public boolean intersects(Rect comp) {
		Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(kulma), x + width / 2, y + height / 2);
		Shape rotatedRect = at.createTransformedShape(rect);
		
		rect = new Rectangle2D.Double(comp.getX(), comp.getY(), comp.getWidth(), comp.getHeight());
		return RageGame.shapeIntersects(rotatedRect, rect);
	}
}
