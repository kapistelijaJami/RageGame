package ragegame;

import ragegame.objects.Wall;
import java.awt.Color;
import java.awt.Graphics;
import ragegame.objects.DiagonalWall;
import ragegame.objects.SpinningWall;

public class Player extends Rect {
	private double speedY;
	private double speedX;
	private RageGame peli;
	
	private boolean lastDirectionLeft;
	private boolean isPressedA;
	private boolean isPressedD;
	
	private boolean jumped = true;
	private boolean isSmall = false;
	private boolean dead = false;
	
	private final double maxSpeed = 20;
	private double lastSpeedY;
	private double lastSpeedX;
	
	public Player(int x, int y, int width, int height, RageGame peli) {
		super(x, y, width, height);
		
		this.peli = peli;
		speedY = 0;
		speedX = 0;
		lastSpeedY = 0;
		lastSpeedX = 0;
		lastDirectionLeft = false;
		isPressedA = false;
		isPressedD = false;
	}

	public double getSpeedY() {
		return speedY;
	}

	public void setSpeedY(double speedY) {
		this.lastSpeedY = this.speedY;
		this.speedY = speedY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedX(double speedX) {
		this.lastSpeedX = this.speedX;
		this.speedX = speedX;
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
	
	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public double getLastSpeedX() {
		return lastSpeedX;
	}
	
	public double getLastSpeedY() {
		return lastSpeedY;
	}
	
	
	public void update() {
		
		updateSpeed();
		
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
		if (speedX < 0) {
			Wall wall = peli.intersectsWithWall(getLeftHitbox()); //LEFT
			if (wall != null) {
				if (wall instanceof DiagonalWall) {
					while (wall.intersects(getLeftHitbox())) {
						x++;
					}
				} else {
					setX(wall.getX() + wall.getWidth());
				}
				setSpeedX(0);
			}
		} else if (speedX > 0) {
			Wall wall = peli.intersectsWithWall(getRightHitbox()); //RIGHT
			if (wall != null) {
				if (wall instanceof DiagonalWall) {
					while (wall.intersects(getRightHitbox())) {
						x--;
					}
				} else {
					setX(wall.getX() - width);
				}
				setSpeedX(0);
			}
		}
	}
	
	public void collisionY() {
		if (speedY < 0) {
			Wall wall = peli.intersectsWithWall(getTopHitbox()); //TOP
			if (wall != null) {
				if (wall instanceof DiagonalWall) {
					while (wall.intersects(getTopHitbox())) {
						y++;
					}
				} else {
					if (isSmall) {
						setY(wall.getY() + wall.getHeight() - height / 2);
					} else {
						setY(wall.getY() + wall.getHeight());
					}
				}
				setSpeedY(0);
				
			}
		} else if (speedY > 0)  {
			Wall wall = peli.intersectsWithWall(getBottomHitbox()); //BOTTOM
			if (wall != null) {
				if (wall instanceof DiagonalWall) {
					while (wall.intersects(getBottomHitbox())) {
						y--;
					}
				} else {
					setY(wall.getY() - height);
				}
				setSpeedY(0);
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
	
	public void updateSpeed() {
		if (lastDirectionLeft && isPressedA) {
			speedX = Math.min(speedX, Math.max(speedX - 1, -5));
		} else if (!lastDirectionLeft && isPressedD) {
			speedX = Math.max(speedX, Math.min(speedX + 1, 5));
		}
	}
	
	public void updateMoveX() {
		x += speedX;
	}
	
	public void updateMoveY() {
		y += speedY;
	}
	
	public void updateGravityAndMomentum() {
		speedY = Math.min(speedY + 0.3, maxSpeed);
		
		if (!isPressedA && !isPressedD) { //kumpaakaan ei paineta
			if (Math.abs(speedX) >= 7) {
				if (speedX > 0) {
					speedX = Math.max(speedX - 0.2, 0); //def: 0.6
				} else if (speedX < 0) {
					speedX = Math.min(speedX + 0.2, 0);
				}
			} else {
				if (speedX > 0) {
					speedX = Math.max(speedX - 0.6, 0); //def: 0.6
				} else if (speedX < 0) {
					speedX = Math.min(speedX + 0.6, 0);
				}
			}
		}
	}
	
	public void jump() {
		if (!jumped) {
			this.setSpeedY(-10);
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
		speedY = 0;
		speedX = 0;
		lastSpeedY = 0;
		lastSpeedX = 0;

		lastDirectionLeft = false;
		isPressedA = false;
		isPressedD = false;

		jumped = true;
		isSmall = false;
		dead = false;
	}
}
