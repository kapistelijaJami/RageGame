package ragegame.objects;

import ragegame.Player;
import ragegame.RageGame;
import ragegame.Rect;

public class MovingWall extends Wall {
	private int startingPoint;
	private int endingPoint;
	private double speed;
	private double maxSpeed;
	private int direction; //0, 1; x, y
	private boolean movingPositive;
	private boolean movesPlayerWith;
	private RageGame peli;
	
	public MovingWall(int x, int y, int width, int height, int startingPoint, int endingPoint, double maxSpeed, int direction, RageGame peli) {
		super(x, y, width, height);
		
		this.startingPoint = startingPoint;
		this.endingPoint = endingPoint;
		this.maxSpeed = maxSpeed;
		this.direction = direction;
		this.speed = 0;
		this.movingPositive = true;
		movesPlayerWith = true;
		this.peli = peli;
	}
	
	public void setMovesPlayerWith(boolean movesPlayerWith) {
		this.movesPlayerWith = movesPlayerWith;
	}
	
	@Override
	public void update() {
		updateSpeed();
		move();
	}
	
	
	public void updateSpeed() {
		if (!isBetweenMoveZone()) {
			movingPositive = !positionIsGreaterThanStart();
		}
		
		if (movingPositive) {
			speed = Math.min(speed + 0.3, maxSpeed);
		} else {
			speed = Math.max(speed - 0.3, -maxSpeed);
		}
	}
	
	public void move() {
		if (direction == 0) {
			x += (int) speed;
			
			if (movesPlayerWith && peli.intersectsWithPlayer(new Rect(x, y - 1, width, height))) {
				Player player = peli.getPlayer();
				int newX = player.getX() + (int) speed;
				player.setX(newX);
			}
		} else if (direction == 1) {
			y += (int) speed;
		}
	}
	
	public boolean isBetweenMoveZone() {
		if (direction == 0) {
			if (x >= startingPoint && x <= endingPoint) {
				return true;
			}
		} else if (direction == 1) {
			if (y >= startingPoint && y <= endingPoint) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean positionIsGreaterThanStart() {
		if (direction == 0) {
			return x > startingPoint;
		} else if (direction == 1) {
			return y > startingPoint;
		}
		
		return true;
	}
}
