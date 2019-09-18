package ragegame;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Rect {
	private double nopeusY;
	private double nopeusX;
	private RageGame peli;
	
	private boolean lastDirectionLeft;
	private boolean isPressedA;
	private boolean isPressedD;
	
	private boolean jumped = true;
	private boolean isSmall = false;
	private boolean dead = false;
	
	private final double maxSpeed = 20;
	
	public Player(int x, int y, int width, int height, RageGame peli) {
		super(x, y, width, height);
		
		this.peli = peli;
		nopeusY = 0;
		nopeusX = 0;
		lastDirectionLeft = false;
		isPressedA = false;
		isPressedD = false;
	}

	public double getNopeusY() {
		return nopeusY;
	}

	public void setNopeusY(double nopeusY) {
		this.nopeusY = nopeusY;
	}

	public double getNopeusX() {
		return nopeusX;
	}

	public void setNopeusX(double nopeusX) {
		this.nopeusX = nopeusX;
	}
	
	public void setIsPressedA(boolean isPressedA) {
		this.isPressedA = isPressedA;
		if (isPressedA) {
			lastDirectionLeft = true;
		} else {
			lastDirectionLeft = false;
		}
	}
	
	public void setIsPressedD(boolean isPressedD) {
		this.isPressedD = isPressedD;
		if (isPressedD) {
			lastDirectionLeft = false;
		} else {
			lastDirectionLeft = true;
		}
	}

	public boolean isSmall() {
		return isSmall;
	}

	public void setIsSmall(boolean isSmall) {
		this.isSmall = isSmall;
	}
	
	
	
	public void update() {
		
		updateNopeus();
		updateMoveX();
		collisionX();
		updateMoveY();
		collisionY();
		
		
		updateGravityAndMomentum();
	}
	
	public void render(Graphics g) {
		if (dead) {
			return;
		}
		
		renderPlayer(g);
	}
	
	public void renderPlayer(Graphics g) {
		g.setColor(Color.BLACK);
		int locY = y;
		int locHeight = height;
		if (isSmall) {
			locY = y + height - height / 2;
			locHeight = height / 2;
		}
		
		g.fillRect(x, locY, width, locHeight);
		
		//renderHitboxes(g);
	}
	
	public void renderHitboxes(Graphics g) {
		g.setColor(Color.red);
		
		Rect top = getTopHitbox();
		g.drawRect(top.x, top.y, top.width, top.height);
		Rect right = getRightHitbox();
		g.drawRect(right.x, right.y, right.width, right.height);
		Rect bottom = getBottomHitbox();
		g.drawRect(bottom.x, bottom.y, bottom.width, bottom.height);
		Rect left = getLeftHitbox();
		g.drawRect(left.x, left.y, left.width, left.height);
	}
	
	public void collisionX() {
		if (nopeusX < 0) {
			Wall wall = peli.intersectsWithWall(getLeftHitbox()); //LEFT
			if (wall != null) {
				setX(wall.getX() + wall.getWidth());
				setNopeusX(0);
			}
		} else if (nopeusX > 0) {
			Wall wall = peli.intersectsWithWall(getRightHitbox()); //RIGHT
			if (wall != null) {
				setX(wall.getX() - width);
				setNopeusX(0);
			}
		}
	}
	
	public void collisionY() {
		if (nopeusY < 0) {
			Wall wall = peli.intersectsWithWall(getTopHitbox()); //TOP
			if (wall != null) {
				setY(wall.getY() + wall.getHeight());
				setNopeusY(0);
			}
		} else if (nopeusY > 0)  {
			Wall wall = peli.intersectsWithWall(getBottomHitbox()); //BOTTOM
			if (wall != null) {
				setY(wall.getY() - height);
				setNopeusY(0);
				jumped = false;
			}
		}
	}
	
	public Rect getTopHitbox() {
		int locY = y;
		if (isSmall) {
			locY = y + height / 2;
		}
		return new Rect(x, locY, width, 5);
	}
	
	public Rect getBottomHitbox() {
		return new Rect(x, y + height - 5, width, 5);
	}
	
	public Rect getLeftHitbox() {
		int locY = y;
		int locHeight = height;
		if (isSmall) {
			locY = y + height / 2;
			locHeight = height / 2;
		}
		return new Rect(x, locY, 5, locHeight);
	}
	
	public Rect getRightHitbox() {
		int locY = y;
		int locHeight = height;
		if (isSmall) {
			locY = y + height / 2;
			locHeight = height / 2;
		}
		return new Rect(x + width - 5, locY, 5, locHeight);
	}
	
	public void updateNopeus() {
		if (lastDirectionLeft && isPressedA) {
			nopeusX = Math.max(nopeusX - 1, -5);
		} else if (!lastDirectionLeft && isPressedD) {
			nopeusX = Math.min(nopeusX + 1, 5);
		}
	}
	
	public void updateMoveX() {
		x += nopeusX;
	}
	
	public void updateMoveY() {
		y += nopeusY;
	}
	
	public void updateGravityAndMomentum() {
		nopeusY = Math.min(nopeusY + 0.3, maxSpeed);
		
		if (!isPressedA && !isPressedD) { //kumpaakaan ei paineta
			if (nopeusX > 0) {
				nopeusX = Math.max(nopeusX - 0.6, 0);
			} else if (nopeusX < 0) {
				nopeusX = Math.min(nopeusX + 0.6, 0);
			}
		}
	}
	
	public void jump() {
		if (!jumped) {
			this.setNopeusY(-10);
		}
		jumped = true;
	}

	public void kill() {
		dead = true;
		System.out.println("You are DEAD!");
	}

	public void win() {
		dead = true;
		System.out.println("WINNER WINNER CHICKEN DINNER!");
	}

	public void reset() {
		nopeusY = 0;
		nopeusX = 0;

		lastDirectionLeft = false;
		isPressedA = false;
		isPressedD = false;

		jumped = true;
		isSmall = false;
		dead = false;
	}
}
