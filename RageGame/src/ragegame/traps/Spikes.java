package ragegame.traps;

import java.awt.Color;
import java.awt.Graphics;
import ragegame.RageGame;
import ragegame.Rect;

public class Spikes extends Trap {
	private int direction; //0, 1, 2, 3; up, right, down, left
	private Rect triggerTrapRect;
	private Rect armTrapRect;
	private boolean fired = false;
	//maybe add timer for the trap
	
	public Spikes(int x, int y, int width, int height, int direction, boolean armed, RageGame peli) {
		super(x, y, width, height, armed, peli);
		
		this.direction = direction;
		
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
	
	public void setArmTrapRect(Rect armTrapRect) {
		this.armTrapRect = armTrapRect;
	}
	
	public void setTriggerTrapRect(Rect trigger) {
		this.triggerTrapRect = trigger;
	}
	
	
	@Override
	public void armTrap() {
		this.armed = true;
	}
	
	@Override
	public void fire() {
		if (this.armed) {
			switch(direction) {
				case 0:
					y -= height;
					break;
				case 1:
					x += width;
					break;
				case 2:
					y += height;
					break;
				case 3:
					x -= width;
					break;
			}
		}
		
		fired = true;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		
		/*g.setColor(Color.GREEN); //render trigger
		g.drawRect(triggerTrapRect.getX(), triggerTrapRect.getY(), triggerTrapRect.getWidth(), triggerTrapRect.getHeight());
		
		g.setColor(Color.GREEN); //render armTrap
		g.drawRect(armTrapRect.getX(), armTrapRect.getY(), armTrapRect.getWidth(), armTrapRect.getHeight());*/
	}

	@Override
	public void update() {
		if (armTrapRect != null && peli.intersectsWithPlayer(armTrapRect)) {
			armTrap();
		}
		
		if (armed && peli.intersectsWithPlayer(triggerTrapRect) && !fired) {
			fire();
			peli.killPlayer();
		}
	}
}
