package ragegame.traps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.Player;
import ragegame.RageGame;
import ragegame.Rect;

public class Trampoline extends Trap {
	private int direction; //0, 1, 2, 3; up, right, down, left
	private Rect triggerTrapRect;
	private Rect armTrapRect;
	private double speed;

	public Trampoline(int x, int y, int width, int height, int direction, double speed, boolean armed, RageGame peli) {
		super(x, y, width, height, armed, peli);
		
		this.direction = direction;
		this.speed = speed;
		
		switch(direction) {
			case 0:
				this.triggerTrapRect = new Rect(x, y - height, width, height);
				break;
			case 1:
				this.triggerTrapRect = new Rect(x + width, y, width, height);
				break;
			case 2:
				this.triggerTrapRect = new Rect(x, y + height, width, height);
				break;
			case 3:
				this.triggerTrapRect = new Rect(x - width, y, width, height);
				break;
		}
	}

	@Override
	public void armTrap() {
		
	}

	@Override
	public void fire() {
		if (this.armed) {
			Player player = peli.getPlayer();
			double playerNopeusY = Math.abs(player.getSpeedY()) > Math.abs(player.getLastSpeedY()) ? player.getSpeedY() : player.getLastSpeedY();
			double playerNopeusX = Math.abs(player.getSpeedX()) > Math.abs(player.getLastSpeedX()) ? player.getSpeedX() : player.getLastSpeedX();
			
			double newSpeedY = Math.abs(playerNopeusY) / (player.getMaxSpeed() / 2) * speed;
			double newSpeedX = Math.abs(playerNopeusX) / (player.getMaxSpeed() / 4) * speed;
			
			switch(direction) {
				case 0:
					player.setSpeedY(Math.min(Math.max(-newSpeedY, -speed), -speed / 2));
					player.setJumped(false);
					break;
				case 1:
					player.setSpeedX(Math.max(Math.min(newSpeedX, speed), speed / 2));
					break;
				case 2:
					player.setSpeedY(Math.max(Math.min(newSpeedY, speed), speed / 2));
					break;
				case 3:
					player.setSpeedX(Math.min(Math.max(-newSpeedX, -speed), -speed / 2));
					break;
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void update() {
		if (armTrapRect != null && peli.intersectsWithPlayer(armTrapRect)) {
			armTrap();
		}
		
		if (armed && peli.intersectsWithPlayer(triggerTrapRect)) {
			fire();
		}
	}
}
